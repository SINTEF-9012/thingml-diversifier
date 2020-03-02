#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $PLATFORMDIR
rm -r $PLATFORMDIR/* 2> /dev/null

function generate_encrypt
{
  LANGUAGE=$1
  COMPILER=`echo $LANGUAGE | cut -d'_' -f1`
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir -p $PLATFORMDIR/$LANGUAGE/encrypt
  
  TARGETMODELDOCKER=/thingml-div/target/models/$LANGUAGE/encrypt
  PLATFORMDIRDOCKER=/thingml-div/target/code/$LANGUAGE/encrypt
  
  for i in `seq 0 $((N-1))`; do
  	_docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/encrypt/$LANGUAGE$i
  	_docker run -v $BASEDIR:/thingml-div --entrypoint /bin/sh thingml-div -c "cd $PLATFORMDIRDOCKER/encrypt/$LANGUAGE$i && cloc . > cloc.log"
  done
}


#FIXME: we should not systematically diversify in all modes, just the modes defined in $MODES in setup.sh
function generate
{
  LANGUAGE=$1
  TIMES=$2
  COMPILER=`echo $LANGUAGE | cut -d'_' -f1`
  echo "---- LANGUAGE $LANGUAGE ----"  
  mkdir -p $PLATFORMDIR/$LANGUAGE/diversify$TIMES/static
  mkdir -p $PLATFORMDIR/$LANGUAGE/diversify$TIMES/dynamic

  TARGETMODELDOCKER=/thingml-div/target/models/$LANGUAGE/diversify$TIMES
  PLATFORMDIRDOCKER=/thingml-div/target/code/$LANGUAGE/diversify$TIMES

  echo "-- GENERATING STATIC DIVERSIFIED CODE --"
  for i in `seq 0 $((N-1))`; do
    mkdir $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/static/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/static/$LANGUAGE$i
    _docker run -v $BASEDIR:/thingml-div  --entrypoint /bin/sh thingml-div -c "cd $PLATFORMDIRDOCKER/static/$LANGUAGE$i && cloc . > cloc.log"
  done

  echo "-- GENERATING DYNAMIC DIVERSIFIED CODE --"
  for i in `seq 0 $((N-1))`; do
    mkdir $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/dynamic/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/dynamic/$LANGUAGE$i
    _docker run -v $BASEDIR:/thingml-div  --entrypoint /bin/sh thingml-div -c "cd $PLATFORMDIRDOCKER/dynamic/$LANGUAGE$i && cloc . > cloc.log"
  done
}

### Generate platform code ###
logo
for LANGUAGE in ${LANGUAGES[@]}; do
  mkdir -p $PLATFORMDIR/$LANGUAGE/base
  echo "-- GENERATING BASE CODE --"
  _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $LANGUAGE -s /thingml-div/target/models/$LANGUAGE/$LANGUAGE.thingml -o /thingml-div/target/code/$LANGUAGE/base  
  _docker run -v $BASEDIR:/thingml-div --entrypoint /bin/sh thingml-div -c "cd /thingml-div/target/code/$LANGUAGE/base && cloc . > cloc.log"
  
  generate $LANGUAGE 1 &
  generate $LANGUAGE 2 &
  generate $LANGUAGE 4 &
  generate $LANGUAGE 8 &
  
  generate_encrypt $LANGUAGE &
done
wait
logo
