#!/usr/bin/env bash

N=100 # Number of diversified version to make
LANGUAGES=(nodejs go arduino) # Languages to use

# Please make sure you have the following list of environment variebles set up:
# - THINGML_REGISTRY=/path/to/compilers.registry-2.0.0-SNAPSHOT-jar-with-dependencies.jar
#     The ThingML compiler registry jar (or the ThingMLCli from dist)
# - ARDUINO_DIR=/path/to/Arduino
#     The Ardunio IDE installation folder
# - ARDUINO_BUILDER_DIR=/path/to/arduino-builder
#     Path to arduino-builder folder

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BASEMODELDIR=$DIR/../../resources/experiments
TARGETDIR=$DIR/../../../../target
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
