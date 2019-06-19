#!/usr/bin/env bash
source setup.sh

#$1: language
#$2: base, static, dynamic or both
#$3: id
function build
{
  timeout -k 45s 180s docker build -t $1-$2-$3 .
}

function sortfolded
{
  INPUTFILE=$1
  LANGUAGE=$2

  while read line; do
    newline=${line#*;} #remove the first frame (typically irrelevant, like Thread-1 in Java)
    echo ${newline% *} >> $INPUTFILE.clean  #remove number at the end of the line
    sed -i 's/\[.*\]//' $INPUTFILE.clean #remove all crap between [...] with sed
  done < $INPUTFILE
  sort $INPUTFILE.clean | uniq -u >$INPUTFILE.clean.sorted
  rm -f $INPUTFILE.folded.clean
  cat $INPUTFILE.clean.sorted >> $BASEDIR/target/flamegraph/$LANGUAGE/$INPUTFILE.all
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
  timeout -k 120s 240s docker run -v $(pwd):/data --cap-add=ALL --name $1-$2-$3 $1-$2-$3:latest > ./out.log

  if [ "$LANGUAGE" == "java" ]; then
    $FLAMEGRAPH_DIR/stackcollapse-ljp.awk < traces.txt > java.folded
    $FLAMEGRAPH_DIR/flamegraph.pl java.folded --color=java > perf.java.svg
    echo "Java Flame graph SVG written to $(pwd)/perf.java.svg"
    cp *.map /tmp/.
    sortfolded java.folded $LANGUAGE
  fi

  #for some reasons, does not work inside the container... we need to run it locally...
  perf script > out.perf
  $FLAMEGRAPH_DIR/stackcollapse-perf.pl out.perf > generic.folded
  $FLAMEGRAPH_DIR/flamegraph.pl generic.folded > perf.generic.svg
  echo "Generic Flame graph SVG written to $(pwd)/perf.generic.svg"
  sortfolded generic.folded $LANGUAGE

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

function diffmetric2
{
  #TODO: count $BASEDIR/target/flamegraph/$LANGUAGE/generic.folded.all.clean.sorted - count $MODE/$ID/$DIFFMODE.folded.clean.sorted
}

function dodiff
{
  DIFFMODE=$1
  LANGUAGE=$2
  MODE=$3
  ID=$4
  MODE2=$5
  ID2=$6

  $FLAMEGRAPH_DIR/difffolded.pl -n -s $MODE/$ID/$DIFFMODE.folded $MODE2/$ID2/$DIFFMODE.folded > $MODE/$ID/diff.$DIFFMODE.$MODE2.$ID2.out
  $FLAMEGRAPH_DIR/flamegraph.pl $MODE/$ID/diff.$DIFFMODE.$MODE2.$ID2.out > $MODE/$ID/diff.$DIFFMODE.$MODE2.$ID2.svg
  echo "$MODE $ID / $MODE2 $ID2 = $(diffmetric $BASEDIR/target/flamegraph/$LANGUAGE/$MODE/$ID/diff.$DIFFMODE.$MODE2.$ID2.out)" >> $(pwd)/diff.$DIFFMODE.log

  git diff --no-index $MODE/$ID/$DIFFMODE.folded.clean.sorted $MODE2/$ID2/$DIFFMODE.folded.clean.sorted > $MODE/$ID/git.diff.$DIFFMODE.$MODE2.$ID2.out
}

#$1 = language
#$2 = ID
function diff
{
  LANGUAGE=$1
  ID=$2
  ID2=$3
  MODE=$4
  MODE2=$5

  cd $BASEDIR/target/flamegraph/$LANGUAGE/

  if [ "$LANGUAGE" == "java" ]; then
    dodiff "java" $LANGUAGE $MODE $ID $MODE2 $ID2
  fi

  dodiff "generic" $LANGUAGE $MODE $ID $MODE2 $ID2
}

for LANGUAGE in ${LANGUAGES[@]}; do
  sort $BASEDIR/target/flamegraph/$LANGUAGE/generic.folded.all | uniq -u >$BASEDIR/target/flamegraph/$LANGUAGE/generic.folded.all.clean.sorted
  if [ "$LANGUAGE" == "java" ]; then
    sort $BASEDIR/target/flamegraph/$LANGUAGE/java.folded.all | uniq -u >$BASEDIR/target/flamegraph/$LANGUAGE/java.folded.all.clean.sorted
  fi
  for i in `seq 0 $((N-1))`; do
    for j in `seq 0 $((N-1))`; do
      diff $LANGUAGE $i $j "base" "base"
      diff $LANGUAGE $i $j "base" "static"
      diff $LANGUAGE $i $j "base" "dynamic"
    done
  done
done
