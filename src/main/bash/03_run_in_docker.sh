#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $LOGSDIR
rm -r $LOGSDIR/* 2> /dev/null

#$1: language
#$2: base, static, dynamic or both
function build
{
  docker build -t $1-$2 .
}

#$1: language
#$2: base, static, dynamic or both
#$3: id
#$4: nolog (optional)
function run
{
  LANGUAGE=$1
  MODE=$2
  LOGS=$3

  _docker2 run --rm $1-$2:latest > $LOGS
}

#$1: language
#$2: base, static, dynamic or both
function clean2
{
  docker rmi -f $1-$2
}

function clean3
{
  docker rmi -f $(docker images -q --filter "dangling=true")
}

function perform
{
  LANGUAGE=$2
  MODE=$3
  LOGS=$4

  cd $1
  build $LANGUAGE $MODE
  run $LANGUAGE $MODE $LOGS
  clean2 $LANGUAGE $MODE
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
    perform $PLATFORMDIR/$LANGUAGE/$MODE $LANGUAGE $MODE $LOGSDIR/$LANGUAGE/base/$i.log
  elif [ "$MODE" == "encrypt" ]; then
    for ENCRYPTMODE in ${ENCRYPTMODES[@]}; do
  	  for ENCRYPTSIZE in ${ENCRYPTSIZES[@]}; do
  	    mkdir -p $LOGSDIR/$LANGUAGE/$ENCRYPTMODE$ENCRYPTSIZE/
        perform $PLATFORMDIR/$LANGUAGE/$ENCRYPTMODE$ENCRYPTSIZE/$LANGUAGE$i $LANGUAGE $MODE $LOGSDIR/$LANGUAGE/$ENCRYPTMODE$ENCRYPTSIZE/$i.log
      done
    done
  else
    perform $PLATFORMDIR/$LANGUAGE/diversify$TIMES/$MODE/$LANGUAGE$i $LANGUAGE $MODE $LOGSDIR/$LANGUAGE/diversify$TIMES/$MODE/$i.log
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
