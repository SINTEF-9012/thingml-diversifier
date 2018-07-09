#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $MODELSDIR
rm -r $MODELSDIR/* 2> /dev/null

### Generate models ###
for LANGUAGE in ${LANGUAGES[@]}; do
    echo "---- LANGUAGE $LANGUAGE ----"
    mkdir -p $MODELSDIR/$LANGUAGE
    mkdir -p $MODELSDIR/$LANGUAGE/static
    mkdir -p $MODELSDIR/$LANGUAGE/dynamic

    BASEMODEL=$BASEMODELDIR/$LANGUAGE.thingml

    echo "-- ADDING LOGGING TO BASE MODEL --"
    java -jar $DIVERSIFIER $BASEMODEL 1 onlylogs $MODELSDIR/$LANGUAGE 1

    echo "-- GENERATING STATIC DIVERSIFICATED MODELS --"
    java -jar $DIVERSIFIER $BASEMODEL $N default $MODELSDIR/$LANGUAGE/static 1

    echo "-- GENERATING DYNAMIC DIVERSIFICATED MODELS --"
    java -jar $DIVERSIFIER $BASEMODEL $N runtime $MODELSDIR/$LANGUAGE/dynamic 1
done
