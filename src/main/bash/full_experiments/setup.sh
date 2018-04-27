#!/usr/bin/env bash

# Run both base and diversified ThingML models N times, and store output logs (NOT VALID)
N=3
#LANGUAGES=(nodejs)
#LANGUAGES=(arduino)
LANGUAGES=(go)
#LANGUAGES=(arduino nodejs go)

# Please make sure you have set a THINGML_REGISTRY environment variable to point to the compiler.registry jar, e.g.
# export THINGML_REGISTRY=/path/to/compilers.registry-2.0.0-SNAPSHOT-jar-with-dependencies.jar

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BASEMODELDIR=$DIR/../../resources/experiments
TARGETDIR=$DIR/../../../../target
DIVERSIFIER=$TARGETDIR/thingml.diversifier-1.0.0-SNAPSHOT-jar-with-dependencies.jar
MODELSDIR=$TARGETDIR/thingml-models
PLATFORMDIR=$TARGETDIR/thingml-platform
LOGSDIR=$TARGETDIR/thingml-logs
WORKDIR=$TARGETDIR/thingml-out

ARDUINO_DIR=/c/usr/Arduino
ARDUINO_BUILDER_DIR=/c/usr/arduino-builder

function unixToWinPath()
{
  REAL=$(realpath $1)
  echo C:${REAL:2}
}
