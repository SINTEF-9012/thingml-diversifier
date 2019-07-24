#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $LOGSDIR
rm -r $LOGSDIR/* 2> /dev/null

#$1: language
#$2: base, static, dynamic or both
#$3: id
function build
{
  timeout -k 45s 180s docker build -t $1-$2-$3 .
}

#$1: language
#$2: base, static, dynamic or both
#$3: id
#$4: nolog (optional)
function run
{
  ((WITH_PERF)) && echo "docker run -v $LOGSDIR/$1/$2:/data --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest" &&  timeout -k 30s 120s docker run -v $LOGSDIR/$1/$2:/data --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest > $LOGSDIR/$1/$2/$4$1$3.log
  ((!WITH_PERF)) && timeout -k 30s 120s docker run --name $1-$2-$3 $1-$2-$3:latest > $LOGSDIR/$1/$2/$4$1$3.log
}

#$1: language
#$2: base, static, dynamic or both
#$3: id
function clean
{
  docker rm -f $1-$2-$3
}

#$1: language
#$2: base, static, dynamic or both
#$3: id
function clean2
{
  docker rmi -f $1-$2-$3
}

function clean3
{
  docker rmi -f $(docker images -q --filter "dangling=true")
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
  MODE=$2
  i=$3

  mkdir $LOGSDIR/$LANGUAGE/
  mkdir $LOGSDIR/$LANGUAGE/$MODE

  echo "-- RUNNING MODEL $i [$LANGUAGE] in $MODE mode--"
  if [ "$MODE" == "base" ]; then
    perform $PLATFORMDIR/$LANGUAGE/nolog/$MODE $LANGUAGE $MODE $i nolog
  else
    perform $PLATFORMDIR/$LANGUAGE/nolog/$MODE/$LANGUAGE$i $LANGUAGE $MODE $i nolog
  fi

  if [ "$LANGUAGE" == "nodejs" ]; then
    if [ "$MODE" == "base" ]; then
      perform $PLATFORMDIR/$LANGUAGE/$MODE $LANGUAGE $MODE $i ""
    else
      perform $PLATFORMDIR/$LANGUAGE/$MODE/$LANGUAGE$i $LANGUAGE $MODE $i ""
    fi
  fi
}

### Generate platform code ###
logo
echo "---- RUNNING XP ----"
for i in `seq 0 $((N-1))`; do
  for j in $(shuf --input-range=0-$(( ${#MODES[@]} - 1 ))); do
    for k in $(shuf --input-range=0-$(( ${#LANGUAGES[@]} - 1 ))); do
      xp ${LANGUAGES[k]} ${MODES[j]} $i
    done
  done
done
wait
echo "---- CLEANING ----"
clean3
echo "------ DONE ------"
logo
