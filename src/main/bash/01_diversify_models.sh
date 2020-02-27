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

  cp $BASEMODEL $TARGETDIR/models/nolog/$LANGUAGE.thingml
  _docker run -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER --source $BASEMODELDOCKER
  cp $TARGETDIR/models/$LANGUAGE/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE.thingml
  rm -rf $TARGETDIR/models/$LANGUAGE/monitor
  
  _docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n $N -m static -o $TARGETMODELDOCKER/nolog/static -s 2
  for i in `seq 0 $((N-1))`; do
  	_docker run -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER/static --source $TARGETMODELDOCKER/nolog/static/$LANGUAGE$i.thingml
  	cp $TARGETDIR/models/$LANGUAGE/static/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/static/$LANGUAGE$i.thingml
  	rm -rf $TARGETDIR/models/$LANGUAGE/static/monitor
  done
  
  _docker run -v $BASEDIR:/thingml-div thingml-div -i $BASEMODELDOCKER -r 1 -n $N -m dynamic -o $TARGETMODELDOCKER/nolog/dynamic -s 2
  for i in `seq 0 $((N-1))`; do
  	_docker run -v $BASEDIR:/thingml-div thingml-div thingml --tool monitor-bin --output $TARGETMODELDOCKER/dynamic --source $TARGETMODELDOCKER/nolog/dynamic/$LANGUAGE$i.thingml
  	cp $TARGETDIR/models/$LANGUAGE/dynamic/monitor/merged.thingml $TARGETDIR/models/$LANGUAGE/dynamic/$LANGUAGE$i.thingml
  	rm -rf $TARGETDIR/models/$LANGUAGE/dynamic/monitor
  done
}

### Generate models ###
logo
mkdir -p $TARGETDIR/models/nolog/
for LANGUAGE in ${LANGUAGES[@]}; do
  generate $LANGUAGE &
  generate ${LANGUAGE}_encrypt &
done
wait
logo
