#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $PLATFORMDIR
rm -r $PLATFORMDIR/* 2> /dev/null

### Generate platform code ###
for LANGUAGE in ${LANGUAGES[@]}; do
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir -p $PLATFORMDIR/$LANGUAGE/base
  mkdir -p $PLATFORMDIR/$LANGUAGE/static
  mkdir -p $PLATFORMDIR/$LANGUAGE/dynamic

  echo "-- GENERATING BASE MODEL CODE --"
  java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/$LANGUAGE.thingml -o $PLATFORMDIR/$LANGUAGE/base
  if [ "$LANGUAGE" == "arduino" ]; then
    sed -i -Ef ../resouces/addstackcheck $PLATFORMDIR/$LANGUAGE/base/test/test.ino
  fi

  echo "-- GENERATING STATIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 1 $N`; do
    mkdir $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/static/$LANGUAGE$i.thingml -o $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    if [ "$LANGUAGE" == "arduino" ]; then
      sed -i -Ef ../resouces/addstackcheck $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i/test/test.ino
    fi
  done

  echo "-- GENERATING DYNAMIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 1 $N`; do
    mkdir $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    java -jar $THINGML_REGISTRY -c $LANGUAGE -s $MODELSDIR/$LANGUAGE/dynamic/$LANGUAGE$i.thingml -o $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    if [ "$LANGUAGE" == "arduino" ]; then
      sed -i -Ef ../resouces/addstackcheck $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i/test/test.ino
    fi
  done
done
