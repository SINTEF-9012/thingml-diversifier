#!/usr/bin/env bash

N=1 # Number of diversified version to make

WIN=1	#Set to 1 if running on Windows and having problems with Docker volumes or paths...

#LANGUAGES=(java_11_hotspot java_11_openj9 java_8_hotspot java_8_openj9 graal nodejs nodejs_chakra go go_gccgo posix posix_clang posixmt posixmt_clang)
LANGUAGES=(java)
MODES=(base static dynamic)

BASEDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )"/../../../ && pwd)

WITH_PERF=0
((WITH_PERF)) && BASEMODELDIR=$BASEDIR/src/main/resources/experiments1/perf
((!WITH_PERF)) && BASEMODELDIR=$BASEDIR/src/main/resources/experiments1

TARGETDIR=$BASEDIR/target
MODELSDIR=$TARGETDIR/models
PLATFORMDIR=$TARGETDIR/code
LOGSDIR=$TARGETDIR/logs

function _docker
{
((WIN)) && export MSYS_NO_PATHCONV=1
timeout -k 300s 600s docker $@
((WIN)) && export MSYS_NO_PATHCONV=0
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
