#!/usr/bin/env bash

# Run both base and diversified ThingML models N times, and store output logs
N=${1:-5}

# Please make sure you have set a THINGML_REGISTRY environment variable to point to the compiler.registry jar, e.g.
# export THINGML_REGISTRY=/path/to/compilers.registry-2.0.0-SNAPSHOT-jar-with-dependencies.jar

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
MODELNAME=test_js
BASEMODEL=$DIR/../resources/$MODELNAME.thingml
TARGETDIR=$DIR/../../../target
DIVERSIFIER=$TARGETDIR/thingml.diversifier-1.0.0-SNAPSHOT-jar-with-dependencies.jar
MODELSDIR=$TARGETDIR/thingml-models
LOGSDIR=$TARGETDIR/thingml-logs
WORKDIR=$TARGETDIR/thingml-out

mkdir -p $MODELSDIR
mkdir -p $LOGSDIR
mkdir -p $WORKDIR

# Add logs to base model, and generate diversified versions
rm -r $MODELSDIR/* 2> /dev/null
java -jar $DIVERSIFIER $BASEMODEL 1 onlylogs $MODELSDIR
java -jar $DIVERSIFIER $BASEMODEL $N default $MODELSDIR

# Compile the non-diversified model
rm -r $WORKDIR/* 2> /dev/null
java -jar $THINGML_REGISTRY -c nodejs -s $MODELSDIR/$MODELNAME.thingml -o $WORKDIR
(
  cd $WORKDIR
  npm install
)

# Run the non-diversified model N times (for runtime differences)
rm -r $LOGSDIR/* 2> /dev/null
for i in `seq 1 $N`; do
  (
    cd $WORKDIR
    node main.js
  )
  cp $WORKDIR/out.log $LOGSDIR/base_$i.log
done

# Run diversified models
for i in `seq 1 $N`; do
  rm -r $WORKDIR/* 2> /dev/null
  echo $MODELSDIR/$MODELNAME$i.thingml
  java -jar $THINGML_REGISTRY -c nodejs -s $MODELSDIR/$MODELNAME$i.thingml -o $WORKDIR
  (
  	cd $WORKDIR
  	npm install
  	node main.js
  )
  cp $WORKDIR/out.log $LOGSDIR/diversified_$i.log
done

ls -l $LOGSDIR/