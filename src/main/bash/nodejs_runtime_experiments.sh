#!/usr/bin/env bash

# Run both base and diversified ThingML models N times, and store output logs (NOT VALID)
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


# Generate diversified versions STATIC
rm -r $MODELSDIR/* 2> /dev/null
java -jar $DIVERSIFIER $BASEMODEL $N default $MODELSDIR 1

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
  cp $WORKDIR/out.log $LOGSDIR/static_$i.log
done

# Generate diversified versions DYNAMIC
rm -r $MODELSDIR/* 2> /dev/null
java -jar $DIVERSIFIER $BASEMODEL $N runtime $MODELSDIR 1

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
  cp $WORKDIR/out.log $LOGSDIR/runtime_$i.log
done

ls -l $LOGSDIR/