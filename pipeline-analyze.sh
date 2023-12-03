#!/bin/bash

echo "Analyze all libraries"
./gradlew dependencyCheckAggregate || exit
./gradlew jacocoTestReport
./gradlew sonar
