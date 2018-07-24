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
  $ARDUINO_DIR/hardware/tools/avr/bin/avrdude -C $ARDUINO_DIR/hardware/tools/avr/etc/avrdude.conf -v -V -patmega328p -carduino -PCOM3 -b115200 -D -Uflash:w:$HEXFILE:i
}
function run_arduino()
{
  timeout -s SIGKILL 3 putty -serial COM3 -sercfg 115200 -sessionlog $WORKDIR/output.log
}

function compile_nodejs()
{
  cp $DIR/../resources/webpack.config.js $WORKDIR/webpack.config.js
  (cd $WORKDIR; ./node_modules/webpack-cli/bin/cli.js --mode production; cp build/packed.js binary )
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

### Generate platform code ###
for LANGUAGE in ${LANGUAGES[@]}; do
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
  rm -r $WORKDIR/*
  cp -r $PLATFORMDIR/$LANGUAGE/base/* $WORKDIR/
  if [ "$LANGUAGE" == "nodejs" ]; then
    # Run npm install only once. It's slow...
    (cd $WORKDIR; npm install; npm install webpack; npm install webpack-cli)
  fi
  # Compile base only once, it's the same every time
  compile_$LANGUAGE
  mv $WORKDIR/binary $BINSDIR/$LANGUAGE/base/$LANGUAGE.bin
  for i in `seq 1 $N`; do
    echo "!!! $LANGUAGE base $i !!!"
    run_$LANGUAGE
    mv $WORKDIR/output.log $LOGSDIR/$LANGUAGE/base/$LANGUAGE$i.log
  done

  echo "-- RUNNING STATIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 1 $N`; do
    echo "!!! $LANGUAGE static $i !!!"
    if [ "$LANGUAGE" == "nodejs" ]; then
      find $WORKDIR/* -maxdepth 0 -not -name 'node_modules' -exec rm -r {} +
    else
      rm -r $WORKDIR/*
    fi
    cp -r $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i/* $WORKDIR/

    compile_$LANGUAGE
    mv $WORKDIR/binary $BINSDIR/$LANGUAGE/static/$LANGUAGE$i.bin
    run_$LANGUAGE
    mv $WORKDIR/output.log $LOGSDIR/$LANGUAGE/static/$LANGUAGE$i.log
  done

  echo "-- RUNNING DYNAMIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 1 $N`; do
    echo "!!! $LANGUAGE dynamic $i !!!"
    if [ "$LANGUAGE" == "nodejs" ]; then
      find $WORKDIR/* -maxdepth 0 -not -name 'node_modules' -exec rm -r {} +
    else
      rm -r $WORKDIR/*
    fi
    cp -r $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i/* $WORKDIR/

    compile_$LANGUAGE
    mv $WORKDIR/binary $BINSDIR/$LANGUAGE/dynamic/$LANGUAGE$i.bin
    run_$LANGUAGE
    mv $WORKDIR/output.log $LOGSDIR/$LANGUAGE/dynamic/$LANGUAGE$i.log
  done

done
