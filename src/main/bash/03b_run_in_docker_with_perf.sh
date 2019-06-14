#!/usr/bin/env bash
source setup.sh

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
  LANGUAGE=$1
  MODE=$2
  ID=$3
  
  cd $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/
  
  #CONTAINER_ID=$(docker run -v $(pwd):/data -d --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest)
  #docker wait $CONTAINER_ID
  docker run -v $(pwd):/data --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest > ./out.log

  if [ "$LANGUAGE" == "java" ]; then
    $FLAMEGRAPH_DIR/stackcollapse-ljp.awk < traces.txt | $FLAMEGRAPH_DIR/flamegraph.pl --color=java > perf.java.svg    
    echo "Java Flame graph SVG written to $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/perf.java.svg"
    cp *.map /tmp/.
  fi

  #for some reasons, does not work inside the container... we need to run it locally...  
  perf script > out.perf
  $FLAMEGRAPH_DIR/stackcollapse-perf.pl out.perf | $FLAMEGRAPH_DIR/flamegraph.pl --color=java > perf.generic.svg
  echo "Generic Flame graph SVG written to $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/perf.generic.svg"
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
  DIR=$1
  LANGUAGE=$2
  MODE=$3
  i=$4
  nolog=$5

  cd $DIR
  build $LANGUAGE $MODE $i
  run $LANGUAGE $MODE $i $nolog
  clean $LANGUAGE $MODE $i
  clean2 $LANGUAGE $MODE $i
  
  rm -rf $TARGETDIR/FlameGraph
}

function xp
{

  LANGUAGE=$1
  MODE=$2
  i=$3

  mkdir $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/

  echo "-- RUNNING MODEL $i [$LANGUAGE] in $MODE mode--"
  if [ "$MODE" == "base" ]; then
    perform $PLATFORMDIR/$LANGUAGE/nolog/$MODE $LANGUAGE $MODE $i nolog
  else
    perform $PLATFORMDIR/$LANGUAGE/nolog/$MODE/$LANGUAGE$i $LANGUAGE $MODE $i nolog
  fi
}

### Install Flamegraph locally ###
echo "---- Installing FlameGraph ----"
cd $TARGETDIR && git clone git@github.com:brendangregg/FlameGraph.git
FLAMEGRAPH_DIR=$TARGETDIR/FlameGraph

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