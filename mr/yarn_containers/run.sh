#!/bin/bash
rm SleepJob.class
javac -cp `hadoop classpath` ./SleepJob.java
jar cvf SleepJob.jar *.class
# This will spawn 4 containers per Node Manager if each NM has 
# 4 GB of yarn.nodemanager.resource.memory-mb
# (if it's the only job running of course)
hadoop jar ./SleepJob.jar SleepJob -Dmapreduce.map.memory.mb=1024 -m 100 -r 10 -mt 240000

# This will spawn 8 containers per Node Manager if each NM has 
# 4 GB of yarn.nodemanager.resource.memory-mb
# (if it's the only job running of course)
hadoop jar ./SleepJob.jar SleepJob -Dmapreduce.map.memory.mb=512 -m 100 -r 10 -mt 240000

