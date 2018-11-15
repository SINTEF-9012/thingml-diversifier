#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $WORKDIR
rm -r $WORKDIR/* 2> /dev/null
mkdir -p $LOGSDIR
rm -r $LOGSDIR/* 2> /dev/null
mkdir -p $BINSDIR
rm -r $BINSDIR/* 2> /dev/null

function compile_arduino()
{
  $ARDUINO_DIR/arduino-builder -hardware $ARDUINO_DIR/hardware/ -tools $ARDUINO_DIR/hardware/ -tools $ARDUINO_DIR/tools/ -tools $ARDUINO_DIR/tools-builder/ -fqbn arduino:avr:uno -compile -prefs "recipe.hooks.postbuild.1.pattern=cp {build.path}/{build.project_name}.hex $WORKDIR/out.hex" $WORKDIR/test/test.ino
  cp $WORKDIR/out.hex $WORKDIR/binary
  HEXFILE=$(unixToWinPath $WORKDIR/out.hex)
  $ARDUINO_DIR/hardware/tools/avr/bin/avrdude -C $ARDUINO_DIR/hardware/tools/avr/etc/avrdude.conf -v -V -patmega328p -carduino -P$ARDUINO_PORT -b115200 -D -Uflash:w:$HEXFILE:i
}
function run_arduino()
{
  timeout -s SIGKILL 3 putty -serial $ARDUINO_PORT -sercfg 115200 -sessionlog $WORKDIR/output.log
}

function compile_nodejs()
{
  cp $DIR/../resources/webpack.config.js $WORKDIR/webpack.config.js
  (cd $WORKDIR; ./node_modules/webpack-cli/bin/cli.js --mode production > /dev/null; cp build/packed.js binary )
}
function run_nodejs()
{
  (cd $WORKDIR; node main.js)
}

function compile_go()
{
  (cd $WORKDIR; go build *.go; cp test.exe binary)
}
function run_go()
{
  (cd $WORKDIR; ./test.exe > output.log)
}

function compile_posix()
{
  cd $WORKDIR
  timeout -k 10s 60s make 
  cp test binary
}
function run_posix()
{
  cd $WORKDIR
  timeout -k 15s 90s ./test > output.log
}

function perform
{
  LANGUAGE=$1
  MODE=$2
  i=$3
  NOLOG=$4

  echo "!!! $LANGUAGE $MODE $i !!!"
  if [ "$LANGUAGE" == "nodejs" ]; then
    find $WORKDIR/* -maxdepth 0 -not -name 'node_modules' -exec rm -r {} +
  else
    rm -r $WORKDIR/*
  fi
  cp -r $PLATFORMDIR/$LANGUAGE/$NOLOG/$MODE/$LANGUAGE$i/* $WORKDIR/

  compile_$LANGUAGE
  mv $WORKDIR/binary $BINSDIR/$LANGUAGE/$MODE/$LANGUAGE$i.bin
  run_$LANGUAGE
  mv $WORKDIR/output.log $LOGSDIR/$LANGUAGE/$MODE/$LANGUAGE$NOLOG$i.log
}

function xp
{
  LANGUAGE=$1
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir $LOGSDIR/$LANGUAGE/
  mkdir $LOGSDIR/$LANGUAGE/base
  mkdir $LOGSDIR/$LANGUAGE/static
  mkdir $LOGSDIR/$LANGUAGE/dynamic
  mkdir $BINSDIR/$LANGUAGE/
  mkdir $BINSDIR/$LANGUAGE/base
  mkdir $BINSDIR/$LANGUAGE/static
  mkdir $BINSDIR/$LANGUAGE/dynamic

  echo "-- RUNNING BASE MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
	perform $LANGUAGE base 0 ""
	perform $LANGUAGE base 0 nolog
  done

  echo "-- RUNNING STATIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
	perform $LANGUAGE static $i ""
	perform $LANGUAGE static $i nolog
  done

  echo "-- RUNNING DYNAMIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
  	perform $LANGUAGE dynamic $i ""
  	perform $LANGUAGE dynamic $i nolog
  done
}

logo
echo "------ RUNNING LOCALLY ------"
echo "---- RUNNING XP ----"
for LANGUAGE in ${LANGUAGES[@]}; do
  xp $LANGUAGE &
done
wait
echo "------ DONE ------"
logo
