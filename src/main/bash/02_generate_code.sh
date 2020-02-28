#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $PLATFORMDIR
rm -r $PLATFORMDIR/* 2> /dev/null

#FIXME: we should not systematically diversify in all modes, just the modes defined in $MODES in setup.sh
function generate
{
  LANGUAGE=$1
  COMPILER=`echo $LANGUAGE | cut -d'_' -f1`
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir -p $PLATFORMDIR/$LANGUAGE/base
  mkdir -p $PLATFORMDIR/$LANGUAGE/static
  mkdir -p $PLATFORMDIR/$LANGUAGE/dynamic

  TARGETMODELDOCKER=/thingml-div/target/models/$LANGUAGE
  PLATFORMDIRDOCKER=/thingml-div/target/code/$LANGUAGE

  echo "-- GENERATING BASE CODE --"
  if [ "$LANGUAGE" == "nodejs" ]; then
    _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/${LANGUAGE}0.thingml -o $PLATFORMDIRDOCKER/base
  fi
  _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/nolog/${LANGUAGE}0.thingml -o $PLATFORMDIRDOCKER/nolog/base  
  _docker run -v $BASEDIR:/thingml-div --entrypoint /bin/sh thingml-div -c "cd $PLATFORMDIRDOCKER/nolog/base && cloc . > cloc.log"

  echo "-- GENERATING STATIC DIVERSIFIED CODE --"
  for i in `seq 0 $((N-1))`; do
    mkdir $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    if [ "$LANGUAGE" == "nodejs" ]; then
      _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/static/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/static/$LANGUAGE$i
    fi
    _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/nolog/static/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/nolog/static/$LANGUAGE$i
    _docker run -v $BASEDIR:/thingml-div  --entrypoint /bin/sh thingml-div -c "cd $PLATFORMDIRDOCKER/nolog/static/$LANGUAGE$i && cloc . > cloc.log"
  done

  echo "-- GENERATING DYNAMIC DIVERSIFIED CODE --"
  for i in `seq 0 $((N-1))`; do
    mkdir $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    if [ "$LANGUAGE" == "nodejs" ]; then
      _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/dynamic/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/dynamic/$LANGUAGE$i
    fi
    _docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $COMPILER -s $TARGETMODELDOCKER/nolog/dynamic/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/nolog/dynamic/$LANGUAGE$i
    _docker run -v $BASEDIR:/thingml-div  --entrypoint /bin/sh thingml-div -c "cd $PLATFORMDIRDOCKER/nolog/dynamic/$LANGUAGE$i && cloc . > cloc.log"
  done
}

### Generate platform code ###
logo
for LANGUAGE in ${LANGUAGES[@]}; do
  generate $LANGUAGE &
  generate ${LANGUAGE}_encrypt &
done
wait
logo
