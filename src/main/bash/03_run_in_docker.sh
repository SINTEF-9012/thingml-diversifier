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
  docker build -t $1-$2-$3 .
}

#$1: language
#$2: base, static, dynamic or both
#$3: id
#$4: nolog (optional)
function run
{
  ((WITH_PERF)) && echo "docker run -v $LOGSDIR/$1/$2:/data --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest" &&  timeout -k 30s 120s docker run -v $LOGSDIR/$1/$2:/data --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest > $LOGSDIR/$1/$2/$4$1$3.log
  ((!WITH_PERF)) && _docker run --name $1-$2-$3 $1-$2-$3:latest > $LOGSDIR/$1/$2/$4$1$3.log
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

  cd $1
  build $LANGUAGE $MODE $i
  run $LANGUAGE $MODE $i
  clean $LANGUAGE $MODE $i
  clean2 $LANGUAGE $MODE $i
}

function xp
{
  LANGUAGE=$1
  MODE=$2
  i=$3
  TIMES=$4

  mkdir -p $LOGSDIR/$LANGUAGE/
  mkdir -p $LOGSDIR/$LANGUAGE/diversify$TIMES/$MODE

  echo "-- RUNNING MODEL $i [$LANGUAGE] in $MODE mode--"
  if [ "$MODE" == "base" ]; then
    perform $PLATFORMDIR/$LANGUAGE/$MODE $LANGUAGE $MODE $i
  elif [ "$MODE" == "encrypt" ]; then
    perform $PLATFORMDIR/$LANGUAGE/$MODE $LANGUAGE $MODE $i
  else
    perform $PLATFORMDIR/$LANGUAGE/diversify$TIMES/$MODE/$LANGUAGE$i $LANGUAGE $MODE $i
  fi
}

### Generate platform code ###
logo
echo "---- RUNNING XP ----"
for i in `seq 0 $((N-1))`; do
  for j in $(shuf --input-range=0-$(( ${#MODES[@]} - 1 ))); do
    for k in $(shuf --input-range=0-$(( ${#LANGUAGES[@]} - 1 ))); do
      if [ "${MODES[j]}" == "base" ]; then
        xp ${LANGUAGES[k]} ${MODES[j]} $i
        xp ${LANGUAGES[k]} encrypt $i
      else
        xp ${LANGUAGES[k]} ${MODES[j]} $i 1
        xp ${LANGUAGES[k]} ${MODES[j]} $i 2
        xp ${LANGUAGES[k]} ${MODES[j]} $i 4
        xp ${LANGUAGES[k]} ${MODES[j]} $i 8
      fi
    done
  done
done
wait
echo "---- CLEANING ----"
clean3
echo "------ DONE ------"
logo
