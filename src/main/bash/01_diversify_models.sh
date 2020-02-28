#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $MODELSDIR
rm -r $MODELSDIR/* 2> /dev/null


function encrypt
{
  LANGUAGE=$1
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir -p $MODELSDIR/$LANGUAGE
  mkdir -p $MODELSDIR/$LANGUAGE/encrypt
  
  BASEMODEL=$BASEMODELDIR/${LANGUAGE}_encrypt.thingml
  ((WITH_PERF)) && BASEMODELDOCKER=/thingml-div/src/main/resources/experiments1/perf/${LANGUAGE}_encrypt.thingml
  ((!WITH_PERF)) && BASEMODELDOCKER=/thingml-div/src/main/resources/experiments1/${LANGUAGE}_encrypt.thingml
  TARGETMODELDOCKER=/thingml-div/target/models/$LANGUAGE/encrypt
  
  for i in `seq 0 $((N-1))`; do  
    _docker run -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER --source $BASEMODELDOCKER
    cp $TARGETDIR/models/$LANGUAGE/encrypt/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/encrypt/$LANGUAGE$i.thingml
    rm -rf $TARGETDIR/models/$LANGUAGE/encrypt/monitor
    head -n -1 $TARGETDIR/models/$LANGUAGE/encrypt/$LANGUAGE$i.thingml > temp.thingml ; mv temp.thingml $TARGETDIR/models/$LANGUAGE/encrypt/$LANGUAGE$i.thingml
    SEED=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 16 | head -n 1)    
    echo "set log.seed = \"$SEED\"" >> $TARGETDIR/models/$LANGUAGE/encrypt/$LANGUAGE$i.thingml
    echo "}" >> $TARGETDIR/models/$LANGUAGE/encrypt/$LANGUAGE$i.thingml    
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
  ((WITH_PERF)) && BASEMODELDOCKER=/thingml-div/src/main/resources/experiments1/perf/$LANGUAGE.thingml
  ((!WITH_PERF)) && BASEMODELDOCKER=/thingml-div/src/main/resources/experiments1/$LANGUAGE.thingml
  TARGETMODELDOCKER=/thingml-div/target/models/$LANGUAGE/diversify$TIMES

#  _docker run -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER --source $BASEMODELDOCKER
#  cp $TARGETDIR/models/$LANGUAGE/diversify$TIMES/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/diversify$TIMES/$LANGUAGE.thingml
#  rm -rf $TARGETDIR/models/$LANGUAGE/diversify$TIMES/monitor
  
#  _docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n $N -m static -o $TARGETMODELDOCKER/nolog/static -s $TIMES
#  for i in `seq 0 $((N-1))`; do
#  	_docker run -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER/static --source $TARGETMODELDOCKER/nolog/static/$LANGUAGE$i.thingml
#  	cp $TARGETDIR/models/$LANGUAGE/diversify$TIMES/static/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/diversify$TIMES/static/$LANGUAGE$i.thingml
#  	rm -rf $TARGETDIR/models/$LANGUAGE/diversify$TIMES/static/monitor
#  done
  
  _docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n $N -m dynamic -o $TARGETMODELDOCKER/nolog/dynamic -s $TIMES --debug
  for i in `seq 0 $((N-1))`; do
  	_docker run -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER/dynamic --source $TARGETMODELDOCKER/nolog/dynamic/$LANGUAGE$i.thingml
  	cp $TARGETDIR/models/$LANGUAGE/diversify$TIMES/dynamic/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/diversify$TIMES/dynamic/$LANGUAGE$i.thingml
  	rm -rf $TARGETDIR/models/$LANGUAGE/diversify$TIMES/dynamic/monitor
  done
}

### Generate models ###
logo
mkdir -p $TARGETDIR/models/nolog/
mkdir -p $TARGETDIR/models/nolog/
for LANGUAGE in ${LANGUAGES[@]}; do
#  generate $LANGUAGE 1 &
#  generate $LANGUAGE 2 &
#  generate $LANGUAGE 3 &
  generate $LANGUAGE 4 &
#  encrypt $LANGUAGE &
done
wait
logo
