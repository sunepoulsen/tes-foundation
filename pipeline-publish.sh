#!/bin/bash

echo "Publish library to local Maven repository"
./gradlew publishToMavenLocal

if [[ $1 == "--remote" ]]; then
  echo "Publish library to remote repository"
  ./gradlew publish
fi
