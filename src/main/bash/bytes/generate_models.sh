#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $MODELSDIR
rm -r $MODELSDIR/* 2> /dev/null

function generate
{
  LANGUAGE=$1
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir -p $MODELSDIR/$LANGUAGE
  mkdir -p $MODELSDIR/$LANGUAGE/static
  mkdir -p $MODELSDIR/$LANGUAGE/dynamic

  BASEMODEL=$BASEMODELDIR/$LANGUAGE.thingml

  echo "-- ADDING LOGGING TO BASE MODEL --"
  java -jar $DIVERSIFIER -i $BASEMODEL -r 1 -n 1 -m static -o $MODELSDIR/$LANGUAGE/nolog -s code-msg
  java -jar $DIVERSIFIER -i $BASEMODEL -r 1 -n 1 -m static -o $MODELSDIR/$LANGUAGE -s code-msg -s log-msg

  echo "-- GENERATING STATIC DIVERSIFICATED MODELS --"
  java -jar $DIVERSIFIER -i $BASEMODEL -r 1 -n $N -m static -o $MODELSDIR/$LANGUAGE/nolog/static -s shuff-msg -s shuff-param -s add-param -s dup-msg -s split-msg -s code-msg
  java -jar $DIVERSIFIER -i $BASEMODEL -r 1 -n $N -m static -o $MODELSDIR/$LANGUAGE/static -s pre-log-msg -s shuff-msg -s shuff-param -s add-param -s dup-msg -s split-msg -s code-msg -s post-log-msg

  echo "-- GENERATING DYNAMIC DIVERSIFICATED MODELS --"
  java -jar $DIVERSIFIER -i $BASEMODEL -r 1 -n $N -m dynamic -o $MODELSDIR/$LANGUAGE/nolog/dynamic -s shuff-msg -s shuff-param -s add-param -s dup-msg -s split-msg -s code-msg
  java -jar $DIVERSIFIER -i $BASEMODEL -r 1 -n $N -m dynamic -o $MODELSDIR/$LANGUAGE/dynamic -s pre-log-msg -s shuff-msg -s shuff-param -s add-param -s dup-msg -s split-msg -s code-msg -s post-log-msg
}

### Generate models ###
logo
for LANGUAGE in ${LANGUAGES[@]}; do
  generate $LANGUAGE &
done
wait
logo