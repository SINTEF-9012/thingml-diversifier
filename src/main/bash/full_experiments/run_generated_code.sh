#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $WORKDIR
rm -r $WORKDIR/* 2> /dev/null
mkdir -p $LOGSDIR
rm -r $LOGSDIR/* 2> /dev/null

function compile_arduino()
{
  $ARDUINO_DIR/arduino-builder -hardware $ARDUINO_DIR/hardware/ -tools $ARDUINO_DIR/hardware/ -tools $ARDUINO_DIR/tools/ -tools $ARDUINO_DIR/tools-builder/ -fqbn arduino:avr:uno -compile -prefs "recipe.hooks.postbuild.1.pattern=cp {build.path}/{build.project_name}.hex $WORKDIR/out.hex" $1
  HEXFILE=$(unixToWinPath $WORKDIR/out.hex)
  $ARDUINO_DIR/hardware/tools/avr/bin/avrdude -C $ARDUINO_DIR/hardware/tools/avr/etc/avrdude.conf -v -V -patmega328p -carduino -PCOM3 -b115200 -D -Uflash:w:$HEXFILE:i
}
function run_arduino()
{
  timeout -s SIGKILL 3 putty -serial COM3 -sercfg 115200 -sessionlog $WORKDIR/output.log
}

function run_nodejs()
{
  (cd $WORKDIR; node main.js)
}

function run_go()
{
  (cd $WORKDIR; go run *.go > output.log)
}

### Generate platform code ###
for LANGUAGE in ${LANGUAGES[@]}; do
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir $LOGSDIR/$LANGUAGE/
  mkdir $LOGSDIR/$LANGUAGE/base
  mkdir $LOGSDIR/$LANGUAGE/static
  mkdir $LOGSDIR/$LANGUAGE/dynamic

  echo "-- RUNNING BASE MODEL CODE --"
  cp -r $PLATFORMDIR/$LANGUAGE/base/* $WORKDIR/
  if [ "$LANGUAGE" == "arduino" ]; then
    compile_arduino $WORKDIR/test/test.ino
  elif [ "$LANGUAGE" == "nodejs" ]; then
    # We don't need to re-install later, they use the same libraries
    (cd $WORKDIR; npm install)
  fi
  for i in `seq 1 $N`; do
    run_$LANGUAGE
    mv $WORKDIR/output.log $LOGSDIR/$LANGUAGE/base/$LANGUAGE$i.log
  done

  echo "-- RUNNING STATIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 1 $N`; do
    cp -r $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i/* $WORKDIR/
    echo "!!! $LANGUAGE static $i !!!"
    if [ "$LANGUAGE" == "arduino" ]; then
      compile_arduino $WORKDIR/test/test.ino
    fi

    run_$LANGUAGE
    mv $WORKDIR/output.log $LOGSDIR/$LANGUAGE/static/$LANGUAGE$i.log
  done

  echo "-- RUNNING DYNAMIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 1 $N`; do
    cp -r $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i/* $WORKDIR/
    echo "!!! $LANGUAGE dynamic $i !!!"
    if [ "$LANGUAGE" == "arduino" ]; then
      compile_arduino $WORKDIR/test/test.ino
    fi

    run_$LANGUAGE
    mv $WORKDIR/output.log $LOGSDIR/$LANGUAGE/dynamic/$LANGUAGE$i.log
  done

done
