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
  docker build -t $1-$2-$3 .
}

#$1: language
#$2: base, static or dynamic
#$3: id
function run
{
  docker run --name $1-$2-$3 $1-$2-$3:latest > $LOGSDIR/$1/$2/$1$3.log
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

function xp
{
  LANGUAGE = $1
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir $LOGSDIR/$LANGUAGE/
  mkdir $LOGSDIR/$LANGUAGE/base
  mkdir $LOGSDIR/$LANGUAGE/static
  mkdir $LOGSDIR/$LANGUAGE/dynamic

  echo "-- RUNNING BASE MODEL CODE --"
  cd $PLATFORMDIR/$LANGUAGE/base
  for i in `seq 0 $((N-1))`; do
    build $LANGUAGE base $i
    timeout -k 15s 60s run $LANGUAGE base $i
    clean $LANGUAGE base $i
    clean2 $LANGUAGE base $i
  done

  echo "-- RUNNING STATIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
    cd $PLATFORMDIR/$LANGUAGE/static/$LANGUAGE$i
    build $LANGUAGE static $i
    timeout -k 15s 60s run $LANGUAGE static $i
    clean $LANGUAGE static $i
    clean2 $LANGUAGE static $i
  done

  echo "-- RUNNING DYNAMIC DIVERSIFICATED MODEL CODE --"
  for i in `seq 0 $((N-1))`; do
    cd $PLATFORMDIR/$LANGUAGE/dynamic/$LANGUAGE$i
    build $LANGUAGE dynamic $i
    timeout -k 15s 60s run $LANGUAGE dynamic $i
    clean $LANGUAGE dynamic $i
    clean2 $LANGUAGE dynamic $i
  done
}

### Generate platform code ###
echo "------ RUNNING ON DOCKER ------"
echo "---- LAUNCHING CADVISOR ----"
monitor
for LANGUAGE in ${LANGUAGES[@]}; do
  xp $LANGUAGE &
done
wait
clean3
