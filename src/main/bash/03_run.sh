#!/usr/bin/env bash
source setup.sh

((WITH_PERF)) && ./03b_run_in_docker_with_perf.sh
((!WITH_PERF)) && ./03a_run_in_docker.sh