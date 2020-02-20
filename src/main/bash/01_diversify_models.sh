#!/usr/bin/env bash
source setup.sh

# Make a clean output directory
mkdir -p $MODELSDIR
rm -r $MODELSDIR/* 2> /dev/null


#FIXME: we should not systematically diversify in all modes, just the modes defined in $MODES in setup.sh
function generate
{
  LANGUAGE=$1
  echo "---- LANGUAGE $LANGUAGE ----"
  mkdir -p $MODELSDIR/$LANGUAGE
  mkdir -p $MODELSDIR/$LANGUAGE/static
  mkdir -p $MODELSDIR/$LANGUAGE/dynamic

  BASEMODEL=$BASEMODELDIR/$LANGUAGE.thingml
  ((WITH_PERF)) && BASEMODELDOCKER=/thingml-div/src/main/resources/experiments1/perf/$LANGUAGE.thingml
  ((!WITH_PERF)) && BASEMODELDOCKER=/thingml-div/src/main/resources/experiments1/$LANGUAGE.thingml
  TARGETMODELDOCKER=/thingml-div/target/models/$LANGUAGE

  docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n 1 -m static -o $TARGETMODELDOCKER/nolog -s code-msg
  docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n $N -m static -o $TARGETMODELDOCKER/nolog/static -s 2 -s code-msg
  docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n $N -m dynamic -o $TARGETMODELDOCKER/nolog/dynamic -s 2 -s code-msg


  if [ "$LANGUAGE" == "nodejs" ]; then
    docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n 1 -m static -o $TARGETMODELDOCKER -s code-msg -s log-msg
    docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n $N -m static -o $TARGETMODELDOCKER/static -s code-msg -s pre-log-msg -s 2 -s code-msg -s post-log-msg
    docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n $N -m dynamic -o $TARGETMODELDOCKER/dynamic -s code-msg -s pre-log-msg -s 2 -s code-msg -s post-log-msg
  fi
}

### Generate models ###
logo
for LANGUAGE in ${LANGUAGES[@]}; do
  generate $LANGUAGE &
done
wait
logo
