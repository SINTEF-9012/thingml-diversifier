#!/usr/bin/env bash

N=1 # Number of diversified version to make
LANGUAGES=(java graal nodejs go posix) # posixmt) # Languages to use
MODES=(base) # static dynamic)

DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
BASEDIR=$DIR/../../..

WITH_PERF=1
((WITH_PERF)) && BASEMODELDIR=$BASEDIR/src/main/resources/experiments1/perf
((!WITH_PERF)) && BASEMODELDIR=$BASEDIR/src/main/resources/experiments1

TARGETDIR=$BASEDIR/target
MODELSDIR=$TARGETDIR/models
PLATFORMDIR=$TARGETDIR/code
LOGSDIR=$TARGETDIR/logs

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
