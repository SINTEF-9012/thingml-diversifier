#!/usr/bin/env bash

./00_build_thingml.sh

./01_diversify_models.sh

./02_generate_code.sh

./03_run_in_docker.sh
