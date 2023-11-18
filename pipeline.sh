#!/bin/bash

JAVA_VERSION=17.0.8.1-tem

echo "Select tools"

export SDKMAN_DIR="$HOME/.sdkman"
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"

sdk use java $JAVA_VERSION

java --version

echo
./pipeline-clean.sh "$@"

echo
./pipeline-build.sh "$@"

echo
./pipeline-publish.sh "$@"

echo
./pipeline-analyze.sh "$@"
