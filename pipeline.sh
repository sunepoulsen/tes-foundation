#!/bin/bash

source ./pipeline-tools.sh

echo
./pipeline-clean.sh "$@"

echo
./pipeline-build.sh "$@"

echo
./pipeline-publish.sh "$@"

echo
./pipeline-analyze.sh "$@"
