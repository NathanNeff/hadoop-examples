#!/bin/bash
set -e
JAR_FILE=SimpleDriver.jar
rm -f *.class
rm -f SimpleDriver.jar
javac -Xlint:deprecation -cp `hadoop classpath` *java
jar cvf $JAR_FILE *.class
test -d outputDir && rm -rf outputDir

hadoop jar $JAR_FILE SimpleDriver -fs=file:/// -jt=local -Dmapred.job.name="Simple THIS!" somedata.txt outputDir
