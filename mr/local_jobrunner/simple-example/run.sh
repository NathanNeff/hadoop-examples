#!/bin/bash
set -e
JAR_FILE=SimpleDriver.jar
./compile.sh || exit 1
test -d outputDir && rm -rf outputDir

hadoop jar $JAR_FILE SimpleDriver -fs=file:/// -jt=local -Dmapred.job.name="Simple THIS!" somedata.txt outputDir
