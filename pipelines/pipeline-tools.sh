#!/bin/bash

JAVA_VERSION=21.0.1-tem

echo "Select tools"

export SDKMAN_DIR="$HOME/.sdkman"
[[ -s "$HOME/.sdkman/bin/sdkman-init.sh" ]] && source "$HOME/.sdkman/bin/sdkman-init.sh"

sdk use java $JAVA_VERSION
java --version
