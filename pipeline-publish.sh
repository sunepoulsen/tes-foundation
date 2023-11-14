#!/bin/bash

echo "Publish library to local Maven repository"
./gradlew publishToMavenLocal

echo "Publish library to remote repository"
./gradlew publish
