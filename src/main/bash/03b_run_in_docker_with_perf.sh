#!/usr/bin/env bash
source setup.sh

#$1: language
#$2: base, static, dynamic or both
function build
{
  DIR=$(pwd)

  if [ "$MODE" == "base" ]; then
    cd $PLATFORMDIR/$LANGUAGE/nolog/$MODE/
    timeout -k 45s 180s docker build -t $1-$2 --build-arg PROFILER_FREQ=$3 .
  else
    cd $PLATFORMDIR/$LANGUAGE/nolog/$MODE/${LANGUAGE}0/
    timeout -k 45s 180s docker build -t $1-$2 --build-arg PROFILER_FREQ=$3 .
  fi
  cd $DIR
}

function sortfolded
{
  INPUTFILE=$1

  while read line; do
    line=${line#*;} #remove the first two frames (typically irrelevant, like Thread-1 in Java)
    line=${line#*;}
    echo ${line% *} >> $INPUTFILE.clean  #remove number at the end of the line
    sed -i 's/\[.*\]//g' $INPUTFILE.clean #remove all crap between [...] with sed
    sed -i 's/Interpreter//g' $INPUTFILE.clean
    sed -i 's/[0-9]//g' $INPUTFILE.clean #remove all numbers to avoid execution-specific IDs in traces
    sed -i 's/\.\./\./g' $INPUTFILE.clean #replace .. by . (can happen once numbers have been removed)
    sed -i 's/;;/;/g' $INPUTFILE.clean
    sed -i 's/^;//g' $INPUTFILE.clean #remove ; if that is the first character on a line
  done < $INPUTFILE
  sort $INPUTFILE.clean | uniq -u >$INPUTFILE.clean.sorted
  #rm -f $INPUTFILE.clean
}

#$1: language
#$2: base, static, dynamic or both
function run
{
  LANGUAGE=$1
  MODE=$2

  DIR=$BASEDIR/target/flamegraph/$LANGUAGE/$MODE

  timeout -k 120s 240s docker run -v $DIR:/data --cap-add=ALL --name $1-$2 $1-$2:latest >> $DIR/out.log

  if [ "$LANGUAGE" == "java" ]; then
    $FLAMEGRAPH_DIR/stackcollapse-ljp.awk < $DIR/traces.txt >> $DIR/java.folded
    cp $DIR/*.map /tmp/.
    cat $DIR/java.folded >> $DIR/../java.folded
  fi

  #for some reasons, does not work inside the container... we need to run it locally...
  perf script -i $DIR/perf.data > $DIR/out.perf
  $FLAMEGRAPH_DIR/stackcollapse-perf.pl $DIR/out.perf >> $DIR/generic.folded
  cat $DIR/generic.folded >> $DIR/../generic.folded

  #if [ "$LANGUAGE" == "java" ]; then
  #  rm -f /tmp/*.map
  #fi
}

#$1: language
#$2: base, static, dynamic or both
function clean
{
  docker rm -f $1-$2
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

### Install Flamegraph locally ###
echo "---- Installing FlameGraph ----"
mkdir -p $TARGETDIR/FlameGraph
git clone https://github.com/brendangregg/FlameGraph.git $TARGETDIR/FlameGraph
FLAMEGRAPH_DIR=$TARGETDIR/FlameGraph

FREQS=(97 193 283) #Frequencies at which the profiler will be executed

function xp
{
  LANGUAGE=$1
  MODE=$2
  FREQ=$3

  echo "-- RUNNING [$LANGUAGE] in $MODE mode, profiling at $FREQ Hz--"
  build $LANGUAGE $MODE $FREQ
  run $LANGUAGE $MODE
  clean $LANGUAGE $MODE
  #clean2 $LANGUAGE $MODE
}

### Generate platform code ###
logo
echo "---- RUNNING XP ----"

for j in $(shuf --input-range=0-$(( ${#MODES[@]} - 1 ))); do
  for k in $(shuf --input-range=0-$(( ${#LANGUAGES[@]} - 1 ))); do

    LANGUAGE=${LANGUAGES[k]}
    MODE=${MODES[j]}

    mkdir -p $BASEDIR/target/flamegraph/$LANGUAGE/$MODE

    for FREQ in ${FREQS[@]}; do
      xp $LANGUAGE $MODE $FREQ
    done

  done
done
wait
echo "---- CLEANING ----"
clean3

function diffmetric
{
  LANGUAGE=$1
  MODE=$2
  DIFFMODE=$3

  ALL=$(wc -l $BASEDIR/target/flamegraph/$LANGUAGE/$DIFFMODE.folded.clean.sorted | awk '{ print $1 }')
  CURRENT=$(wc -l $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/$DIFFMODE.folded.clean.sorted | awk '{ print $1 }')
  OP="scale=2; $CURRENT/$ALL*100"
  RESULT=$(bc -l <<< $OP)
  echo "$CURRENT/$ALL = $RESULT %"
}

function dodiff
{
  DIFFMODE=$1
  LANGUAGE=$2
  MODE=$3
  MODE2=$4

  DIR=$BASEDIR/target/flamegraph/$LANGUAGE/

  $FLAMEGRAPH_DIR/difffolded.pl -n -s $DIR/$MODE/$DIFFMODE.folded $DIR/$MODE2/$DIFFMODE.folded > $DIR/$MODE/diff.$DIFFMODE.$MODE2.out
  $FLAMEGRAPH_DIR/flamegraph.pl $DIR/$MODE/diff.$DIFFMODE.$MODE2.out > $DIR/$MODE/diff.$DIFFMODE.$MODE2.svg
  echo "$(diffmetric $LANGUAGE $MODE2 $DIFFMODE)" > $DIR/$MODE2/diff.$DIFFMODE.log
  git diff --no-index $DIR/$MODE/$DIFFMODE.folded.clean.sorted $DIR/$MODE2/$DIFFMODE.folded.clean.sorted > $DIR/$MODE2/git.diff.$DIFFMODE.$MODE.out
}

function fg
{
  LANGUAGE=$1
  MODE=$2

  $FLAMEGRAPH_DIR/flamegraph.pl $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/generic.folded > $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/perf.generic.svg
  echo "Generic Flame graph SVG written to $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/perf.generic.svg"
  if [ "$LANGUAGE" == "java" ]; then
    $FLAMEGRAPH_DIR/flamegraph.pl $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/java.folded > $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/perf.java.svg
    echo "Java Flame graph SVG written to $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/perf.java.svg"
  fi
}

#$1 = language
#$2 = ID
function diff
{
  LANGUAGE=$1
  MODE=$2
  MODE2=$3

  if [ "$LANGUAGE" == "java" ]; then
    dodiff "java" $LANGUAGE $MODE $MODE2
  fi

  dodiff "generic" $LANGUAGE $MODE $MODE2
}

for LANGUAGE in ${LANGUAGES[@]}; do
  fg $LANGUAGE "base"
  fg $LANGUAGE "static"
  fg $LANGUAGE "dynamic"

  sortfolded $BASEDIR/target/flamegraph/$LANGUAGE/generic.folded
  for MODE in ${MODES[@]}; do
    sortfolded $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/generic.folded
  done
  if [ "$LANGUAGE" == "java" ]; then
    sortfolded $BASEDIR/target/flamegraph/$LANGUAGE/java.folded
    for MODE in ${MODES[@]}; do
      sortfolded $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/java.folded
    done
  fi

  for MODE in ${MODES[@]}; do
    diff $LANGUAGE "base" $MODE
  done

done

echo "------ DONE ------"
logo
