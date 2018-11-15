#!/usr/bin/env bash

N=1 # Number of diversified version to make
LANGUAGES=(java nodejs go) # Languages to use
#LANGUAGES=(posix) # Languages to use

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BASEDIR=$DIR/../../../..

# We assume ThingML has been cloned (and compiled) besides thingml-diversifier
THINGML_REGISTRY=$BASEDIR/../ThingML/compilers/registry/target/compilers.registry-2.0.0-SNAPSHOT-jar-with-dependencies.jar
# Need to be adjusted if running the Arduino experiments
ARDUINO_DIR=/c/Program\ Files\ \(x86\)/Arduino
ARDUINO_PORT=COM3

#BASEMODELDIR=$DIR/../../resources/experiments
BASEMODELDIR=$DIR/../../resources/experiments1
TARGETDIR=$BASEDIR/target
DIVERSIFIER=$TARGETDIR/thingml.diversifier-1.0.0-SNAPSHOT-jar-with-dependencies.jar
MODELSDIR=$TARGETDIR/thingml-bytes-models
PLATFORMDIR=$TARGETDIR/thingml-bytes-platform
LOGSDIR=$TARGETDIR/thingml-bytes-logs
BINSDIR=$TARGETDIR/thingml-bytes-bins
WORKDIR=$TARGETDIR/thingml-out


function unixToWinPath()
{
  REAL=$(realpath $1)
  echo C:${REAL:2}
}

function logo
{
echo "  _____ _     _             __  __ _           ";
echo " |_   _| |__ (_)_ __   __ _|  \/  | |          ";
echo "   | | | '_ \| | '_ \ / _\` | |\/| | |          ";
echo "   | | | | | | | | | | (_| | |  | | |___       ";
echo "   |_| |_| |_|_|_| |_|\__, |_|  |_|_____|      ";
echo "  ____  _             |___/  _  __ _           ";
echo " |  _ \(_)_   _____ _ __ ___(_)/ _(_) ___ _ __ ";
echo " | | | | \ \ / / _ \ '__/ __| | |_| |/ _ \ '__|";
echo " | |_| | |\ V /  __/ |  \__ \ |  _| |  __/ |   ";
echo " |____/|_| \_/ \___|_|  |___/_|_| |_|\___|_|   ";
echo "                                               ";
}