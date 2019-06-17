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

  mkdir -p $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/$ID/
  cd $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/$ID

  #CONTAINER_ID=$(docker run -v $(pwd):/data -d --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest)
  #docker wait $CONTAINER_ID
  docker run -v $(pwd):/data --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest > ./out.log

  if [ "$LANGUAGE" == "java" ]; then
    $FLAMEGRAPH_DIR/stackcollapse-ljp.awk < traces.txt > java.folded
    $FLAMEGRAPH_DIR/flamegraph.pl java.folded --color=java > perf.java.svg
    echo "Java Flame graph SVG written to $(pwd)/perf.java.svg"
    cp *.map /tmp/.
  fi

  #for some reasons, does not work inside the container... we need to run it locally...
  perf script > out.perf
  $FLAMEGRAPH_DIR/stackcollapse-perf.pl out.perf > generic.folded
  $FLAMEGRAPH_DIR/flamegraph.pl generic.folded > perf.generic.svg
  echo "Generic Flame graph SVG written to $(pwd)/perf.generic.svg"

  if [ "$LANGUAGE" == "java" ]; then
    rm -f /tmp/*.map
  fi
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
  #clean2 $LANGUAGE $MODE $i
}

function xp
{

  LANGUAGE=$1
  MODE=$2
  i=$3

  echo "-- RUNNING MODEL $i [$LANGUAGE] in $MODE mode--"
  if [ "$MODE" == "base" ]; then
    perform $PLATFORMDIR/$LANGUAGE/nolog/$MODE $LANGUAGE $MODE $i nolog
  else
    perform $PLATFORMDIR/$LANGUAGE/nolog/$MODE/$LANGUAGE$i $LANGUAGE $MODE $i nolog
  fi
}

### Install Flamegraph locally ###
echo "---- Installing FlameGraph ----"
cd $TARGETDIR && git clone https://github.com/brendangregg/FlameGraph.git
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

#$1 path to diff.out file
function diffmetric
{
  DIFF=$1
  RESULT=0
  while read line; do
    LAST=${line##*;} #line looks like foo;....;bar i j #with i,j integers #this command returns bar i j
    read -ra split <<<"$LAST"
    a=${split[1]}
    b=${split[2]}
    metric=$((a-b))
    metric=${metric#-}
    RESULT=$((RESULT+metric))
  done < $DIFF
  echo "$RESULT"
}

#$1 = language
#$2 = ID
function diff
{
  LANGUAGE=$1
  ID=$2

  cd $BASEDIR/target/flamegraph/$LANGUAGE/

  if [ "$LANGUAGE" == "java" ]; then
    $FLAMEGRAPH_DIR/difffolded.pl -n -s base/$ID/java.folded base/$ID/java.folded > base/$ID/diff.java.out
    $FLAMEGRAPH_DIR/flamegraph.pl base/$ID/diff.java.out > base/$ID/diff.java.svg
    echo "Base-Base $ID Java diff = $(diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/base/$ID/diff.java.out)"

    $FLAMEGRAPH_DIR/difffolded.pl -n -s base/$ID/java.folded static/$ID/java.folded > static/$ID/diff.java.out
    $FLAMEGRAPH_DIR/flamegraph.pl static/$ID/diff.java.out > static/$ID/diff.java.svg
    diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/static/$ID/diff.java.out
    echo "Base-Static $ID Java diff = $(diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/static/$ID/diff.java.out)"

    $FLAMEGRAPH_DIR/difffolded.pl -n -s base/$ID/java.folded dynamic/$ID/java.folded > dynamic/$ID/diff.java.out
    $FLAMEGRAPH_DIR/flamegraph.pl dynamic/$ID/diff.java.out > dynamic/$ID/diff.java.svg
    diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/dynamic/$ID/diff.java.out
    echo "Base-Dynamic $ID Java diff = $(diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/dynamic/$ID/diff.java.out)"
  fi

  $FLAMEGRAPH_DIR/difffolded.pl -n -s base/$ID/generic.folded base/$ID/generic.folded > base/$ID/diff.generic.out
  $FLAMEGRAPH_DIR/flamegraph.pl base/$ID/diff.generic.out > base/$ID/diff.generic.svg
  diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/base/$ID/diff.generic.out
  echo "Base-Base $ID Generic diff = $(diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/base/$ID/diff.generic.out)"

  $FLAMEGRAPH_DIR/difffolded.pl -n -s base/$ID/generic.folded static/$ID/generic.folded > static/$ID/diff.generic.out
  $FLAMEGRAPH_DIR/flamegraph.pl static/$ID/diff.generic.out > static/$ID/diff.generic.svg
  diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/static/$ID/diff.generic.out
  echo "Base-Static $ID Generic diff = $(diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/static/$ID/diff.generic.out)"

  $FLAMEGRAPH_DIR/difffolded.pl -n -s base/$ID/generic.folded dynamic/$ID/generic.folded > dynamic/$ID/diff.generic.out
  $FLAMEGRAPH_DIR/flamegraph.pl dynamic/$ID/diff.generic.out > dynamic/$ID/diff.generic.svg
  diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/dynamic/$ID/diff.generic.out
  echo "Base-Dynamic $ID Generic diff = $(diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/dynamic/$ID/diff.generic.out)"
}

for i in `seq 0 $((N-1))`; do
  for LANGUAGE in ${LANGUAGES[@]}; do
    diff $LANGUAGE $i
  done
done
