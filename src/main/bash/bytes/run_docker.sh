#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $LOGSDIR
rm -r $LOGSDIR/* 2> /dev/null

function monitor
{
  docker run \
    --volume=/:/rootfs:ro \
    --volume=/var/run:/var/run:ro \
    --volume=/sys:/sys:ro \
    --volume=/var/lib/docker/:/var/lib/docker:ro \
    --volume=/dev/disk/:/dev/disk:ro \
    --publish=8080:8080 \
    --detach=true \
    --name=cadvisor \
    google/cadvisor:latest
}

#$1: language
#$2: base, static or dynamic
#$3: id
function build
{
  timeout -k 30s 300s docker build -t $1-$2-$3 .
}

#$1: language
#$2: base, static or dynamic
#$3: id
#$4: nolog (optional)
function run
{
  timeout -k 15s 120s docker run --name $1-$2-$3 $1-$2-$3:latest > $LOGSDIR/$1/$2/$4$1$3.log
}

#$1: language
#$2: base, static or dynamic
#$3: id
function clean
{
  docker rm -f $1-$2-$3
}

#$1: language
#$2: base, static or dynamic
#$3: id
function clean2
{
  docker rmi $1-$2-$3
}

function clean3
{
  docker rmi $(docker images -q --filter "dangling=true")
}

function perform
{
  LANGUAGE=$2
  MODE=$3
  i=$4
  nolog=$5

  cd $1
  build $LANGUAGE $MODE $i
  run $LANGUAGE $MODE $i $nolog
  clean $LANGUAGE $MODE $i
  clean2 $LANGUAGE $MODE $i
}

function xp
{
  LANGUAGE=$1
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir $LOGSDIR/$LANGUAGE/
  mkdir $LOGSDIR/$LANGUAGE/base
  mkdir $LOGSDIR/$LANGUAGE/static
  mkdir $LOGSDIR/$LANGUAGE/dynamic

  echo "-- RUNNING BASE MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
    perform $PLATFORMDIR/$LANGUAGE/base $LANGUAGE base $i ""
    perform $PLATFORMDIR/$LANGUAGE/nolog/base $LANGUAGE base $i nolog
  done

  echo "-- RUNNING STATIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
    perform $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i $LANGUAGE static $i ""
    perform $PLATFORMDIR/$LANGUAGE/nolog/static/$LANGUAGE$i $LANGUAGE static $i nolog
  done

  echo "-- RUNNING DYNAMIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
    perform $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i $LANGUAGE dynamic $i ""
    perform $PLATFORMDIR/$LANGUAGE/nolog/dynamic/$LANGUAGE$i $LANGUAGE dynamic $i nolog
  done
}

### Generate platform code ###
echo "------ RUNNING ON DOCKER ------"
echo "---- LAUNCHING CADVISOR ----"
monitor
echo "---- RUNNING XP ----"
for LANGUAGE in ${LANGUAGES[@]}; do
  xp $LANGUAGE &
done
wait
echo "---- CLEANING ----"
clean3
