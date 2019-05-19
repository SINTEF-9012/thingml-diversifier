#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $PLATFORMDIR
rm -r $PLATFORMDIR/* 2> /dev/null

function generate
{
  LANGUAGE=$1
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir -p $PLATFORMDIR/$LANGUAGE/base
  mkdir -p $PLATFORMDIR/$LANGUAGE/static
  mkdir -p $PLATFORMDIR/$LANGUAGE/dynamic

  BASEMODELDOCKER=/thingml-div/src/main/resources/experiments1/$LANGUAGE.thingml
  TARGETMODELDOCKER=/thingml-div/target/models/$LANGUAGE
  PLATFORMDIRDOCKER=/thingml-div/target/code/$LANGUAGE

  echo "-- GENERATING BASE CODE --"
  if [ "$LANGUAGE" == "nodejs" ]; then
    docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $LANGUAGE -s $TARGETMODELDOCKER/${LANGUAGE}0.thingml -o $PLATFORMDIRDOCKER/base
  fi
  docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $LANGUAGE -s $TARGETMODELDOCKER/nolog/${LANGUAGE}0.thingml -o $PLATFORMDIRDOCKER/nolog/base

  echo "-- GENERATING STATIC DIVERSIFIED CODE --"
  for i in `seq 0 $((N-1))`; do
    mkdir $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    if [ "$LANGUAGE" == "nodejs" ]; then
      docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $LANGUAGE -s $TARGETMODELDOCKER/static/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/static/$LANGUAGE$i
    fi
    docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $LANGUAGE -s $TARGETMODELDOCKER/nolog/static/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/nolog/static/$LANGUAGE$i
  done

  echo "-- GENERATING DYNAMIC DIVERSIFIED CODE --"
  for i in `seq 0 $((N-1))`; do
    mkdir $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    if [ "$LANGUAGE" == "nodejs" ]; then
      docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $LANGUAGE -s $TARGETMODELDOCKER/dynamic/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/dynamic/$LANGUAGE$i
    fi
    docker run -v $BASEDIR:/thingml-div thingml-div thingml -c $LANGUAGE -s $TARGETMODELDOCKER/nolog/dynamic/$LANGUAGE$i.thingml -o $PLATFORMDIRDOCKER/nolog/dynamic/$LANGUAGE$i
  done
}

### Generate platform code ###
logo
for LANGUAGE in ${LANGUAGES[@]}; do
  generate $LANGUAGE &
done
wait
logo
