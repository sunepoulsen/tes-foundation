#!/bin/bash

echo "Analyze all libraries"
./gradlew dependencyCheckAnalyze
./gradlew jacocoTestReport
./gradlew sonar
