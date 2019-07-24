#!/usr/bin/env bash
source setup.sh

rm -rf $TARGETDIR
mkdir -p $TARGETDIR

./00_build_thingml.sh

./01_diversify_models.sh

./02_generate_code.sh

./03_run_in_docker.sh
