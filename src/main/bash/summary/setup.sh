#!/usr/bin/env bash

N=25 # Number of diversified version to make
LANGUAGES=(nodejs go arduino) # Languages to use

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BASEDIR=$DIR/../../../../

#We assume ThingML has been cloned (and compiled) besides thingml-diversifier
THINGML_REGISTRY=$BASEDIR/../ThingML/compilers/registry/target/compilers.registry-2.0.0-SNAPSHOT-jar-with-dependencies.jar

# Need to be adjusted if running the Arduino experiments
ARDUINO_DIR=/c/Program\ Files\ \(x86\)/Arduino
ARDUINO_PORT=COM3

BASEMODELDIR=$DIR/../../resources/experiments
TARGETDIR=$BASEDIR/target
DIVERSIFIER=$TARGETDIR/thingml.diversifier-1.0.0-SNAPSHOT-jar-with-dependencies.jar
MODELSDIR=$TARGETDIR/thingml-models
PLATFORMDIR=$TARGETDIR/thingml-platform
LOGSDIR=$TARGETDIR/thingml-logs
BINSDIR=$TARGETDIR/thingml-bins
WORKDIR=$TARGETDIR/thingml-out

function unixToWinPath()
{
  REAL=$(realpath $1)
  echo C:${REAL:2}
}
