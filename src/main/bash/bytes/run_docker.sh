#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $LOGSDIR
rm -r $LOGSDIR/* 2> /dev/null

function build
{
  docker build -t $1 .
}

function run
{
  docker run --name $1 $1:latest > output.log
}

function clean
{
  docker rm -f $1
}

function clean2
{
  docker rmi $1
}

function clean3
{
  docker rmi $(docker images -q --filter "dangling=true")
}

### Generate platform code ###
echo "------ RUNNING ON DOCKER ------"
for LANGUAGE in ${LANGUAGES[@]}; do
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir $LOGSDIR/$LANGUAGE/
  mkdir $LOGSDIR/$LANGUAGE/base
  mkdir $LOGSDIR/$LANGUAGE/static
  mkdir $LOGSDIR/$LANGUAGE/dynamic

  echo "-- RUNNING BASE MODEL CODE --"
  cd $PLATFORMDIR/$LANGUAGE/base
  build $LANGUAGE-base-0
  for i in `seq 0 $((N-1))`; do
    run $LANGUAGE-base-0
    mv output.log $LOGSDIR/$LANGUAGE/base/$LANGUAGE$i.log
    clean $LANGUAGE-base-0
  done
  clean2 $LANGUAGE-base-0

  echo "-- RUNNING STATIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
    cd $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    build $LANGUAGE-static-$i
    run $LANGUAGE-static-$i
    mv output.log $LOGSDIR/$LANGUAGE/static/$LANGUAGE$i.log
    clean $LANGUAGE-static-$i
    clean2 $LANGUAGE-static-$i
  done

  echo "-- RUNNING DYNAMIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
    cd $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    build $LANGUAGE-dynamic-$i
    run $LANGUAGE-dynamic-$i
    mv output.log $LOGSDIR/$LANGUAGE/dynamic/$LANGUAGE$i.log
    clean $LANGUAGE-dynamic-$i
    clean2 $LANGUAGE-dynamic-$i
  done

  clean3

done
