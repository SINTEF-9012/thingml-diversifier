#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $MODELSDIR
rm -r $MODELSDIR/* 2> /dev/null


function encrypt
{
  LANGUAGE=$1
  echo "---- LANGUAGE $LANGUAGE ----"
  
  BASEMODEL=$BASEMODELDIR/${LANGUAGE}_encrypt.thingml
  
  for ENCRYPTMODE in ${ENCRYPTMODES[@]}; do
  	for ENCRYPTSIZE in ${ENCRYPTSIZES[@]}; do
  		mkdir -p $MODELSDIR/$LANGUAGE/$ENCRYPTMODE$ENCRYPTSIZE
  		cp $BASEDIR/src/main/resources/experiments2/aes.c $TARGETDIR/models/$LANGUAGE/$ENCRYPTMODE$ENCRYPTSIZE/aes.c
  		cp $BASEDIR/src/main/resources/experiments2/aes.h $TARGETDIR/models/$LANGUAGE/$ENCRYPTMODE$ENCRYPTSIZE/aes.h
  		for i in `seq 0 $((N-1))`; do 
  			OUT=$TARGETDIR/models/$LANGUAGE/$ENCRYPTMODE$ENCRYPTSIZE/$LANGUAGE$i.thingml
    		
    		tail -n +2 $BASEMODEL > temp.thingml
    		echo "import \"../../../../src/main/resources/experiments2/MedicalGW.thingml\"" >> $OUT
    		cat temp.thingml >> $OUT
    		rm temp.thingml
    		
    		head -n -1 $OUT > temp.thingml ; mv temp.thingml $OUT
    		(( BYTES = $ENCRYPTSIZE / 8 ))
    		KEY=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w $BYTES | head -n 1)
    		IV=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 16 | head -n 1)    
    		echo "set log.KEY_SIZE = AESKeySize:AES$ENCRYPTSIZE" >> $OUT
    		echo "set log.ALGO = AESAlgorithm:$ENCRYPTMODE" >> $OUT
    		echo "set log.STRING_KEY = \"$KEY\"" >> $OUT
    		echo "set log.STRING_IV = \"$IV\"" >> $OUT
    		echo "}" >> $OUT
    	done
  	done
  done
}

#FIXME: we should not systematically diversify in all modes, just the modes defined in $MODES in setup.sh
function generate
{
  LANGUAGE=$1
  TIMES=$2
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir -p $MODELSDIR/$LANGUAGE
  mkdir -p $MODELSDIR/$LANGUAGE/diversify$TIMES/static
  mkdir -p $MODELSDIR/$LANGUAGE/diversify$TIMES/dynamic

  BASEMODEL=$BASEMODELDIR/$LANGUAGE.thingml
  ((WITH_PERF)) && BASEMODELDOCKER=/thingml-div/src/main/resources/experiments2/perf/$LANGUAGE.thingml
  ((!WITH_PERF)) && BASEMODELDOCKER=/thingml-div/src/main/resources/experiments2/$LANGUAGE.thingml
  TARGETMODELDOCKER=/thingml-div/target/models/$LANGUAGE/diversify$TIMES
  
  _docker run --rm -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -c $LANGUAGE -r 1 -a $N -m static -o $TARGETMODELDOCKER/nolog/static -s $TIMES --noisy
  for i in `seq 0 $((N-1))`; do
  	_docker run --rm -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER/static --source $TARGETMODELDOCKER/nolog/static/$LANGUAGE$i.thingml
  	cp $TARGETDIR/models/$LANGUAGE/diversify$TIMES/static/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/diversify$TIMES/static/$LANGUAGE$i.thingml
  	rm -rf $TARGETDIR/models/$LANGUAGE/diversify$TIMES/static/monitor
  done
  
  _docker run --rm -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -c $LANGUAGE -r 1 -a $N -m dynamic -o $TARGETMODELDOCKER/nolog/dynamic -s $TIMES --noisy
  for i in `seq 0 $((N-1))`; do
  	_docker run --rm -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER/dynamic --source $TARGETMODELDOCKER/nolog/dynamic/$LANGUAGE$i.thingml
  	cp $TARGETDIR/models/$LANGUAGE/diversify$TIMES/dynamic/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/diversify$TIMES/dynamic/$LANGUAGE$i.thingml
  	rm -rf $TARGETDIR/models/$LANGUAGE/diversify$TIMES/dynamic/monitor
  done
}

### Generate models ###
logo
mkdir -p $TARGETDIR/models/nolog/
for LANGUAGE in ${LANGUAGES[@]}; do
  _docker run --rm -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output /thingml-div/target/models/$LANGUAGE --source /thingml-div/src/main/resources/experiments2/$LANGUAGE.thingml
  cp $TARGETDIR/models/$LANGUAGE/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/$LANGUAGE.thingml
  rm -rf $TARGETDIR/models/$LANGUAGE/monitor

  generate $LANGUAGE 1 &
  generate $LANGUAGE 2 &
  generate $LANGUAGE 4 &
  generate $LANGUAGE 8 &
  
  encrypt $LANGUAGE &
done
wait
logo
