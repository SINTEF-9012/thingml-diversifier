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

  echo "-- GENERATING BASE CODE --"
  java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/${LANGUAGE}0.thingml -o $PLATFORMDIR/$LANGUAGE/base
  java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/nolog/${LANGUAGE}0.thingml -o $PLATFORMDIR/$LANGUAGE/nolog/base
  if [ "$LANGUAGE" == "arduino" ]; then
    sed -i -Ef ../resouces/addstackcheck $PLATFORMDIR/$LANGUAGE/base/test/test.ino
  fi

  echo "-- GENERATING STATIC DIVERSIFIED CODE --"
  for i in `seq 0 $((N-1))`; do
    mkdir $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/static/$LANGUAGE$i.thingml -o $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/nolog/static/$LANGUAGE$i.thingml -o $PLATFORMDIR/$LANGUAGE/nolog/static/$LANGUAGE$i
    if [ "$LANGUAGE" == "arduino" ]; then
      sed -i -Ef ../resouces/addstackcheck $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i/test/test.ino
    fi
  done

  echo "-- GENERATING DYNAMIC DIVERSIFIED CODE --"
  for i in `seq 0 $((N-1))`; do
    mkdir $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/dynamic/$LANGUAGE$i.thingml -o $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/nolog/dynamic/$LANGUAGE$i.thingml -o $PLATFORMDIR/$LANGUAGE/nolog/dynamic/$LANGUAGE$i
    if [ "$LANGUAGE" == "arduino" ]; then
      sed -i -Ef ../resouces/addstackcheck $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i/test/test.ino
    fi
  done
}

### Generate platform code ###
logo
for LANGUAGE in ${LANGUAGES[@]}; do
  generate $LANGUAGE &
done
wait
logo
