#!/bin/bash
set -e
JAR_FILE=SimpleDriver.jar
rm -f *.class
rm -f $JAR_FILE
javac -Xlint:deprecation -cp `hadoop classpath` *java
jar cvf $JAR_FILE *.class
