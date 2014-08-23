#!/bin/bash
# Test how many times a naughty process is retried when it violates the container's memory size
set -e
rm -f *.class
javac -cp `hadoop classpath` ./SleepJobWithArray.java
jar cvf SleepJobWithArray.jar *.class
# This will spawn 4 containers per Node Manager if each NM has 
# 4 GB of yarn.nodemanager.resource.memory-mb
# (if it's the only job running of course)
hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.map.memory.mb=256 \
        -Dmapreduce.map.java.opts=-Xms1024m \
        -m 1 -r 1 -mt 30000 
