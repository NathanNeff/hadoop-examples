#!/bin/bash
# Test how many times a naughty process is retried when it violates the container's memory size
set -e
rm -f *.class
javac -cp `hadoop classpath` ./SleepJobWithArray.java
jar cvf SleepJobWithArray.jar *.class

# Max container size is 256 MB, but Java heap is 1024.
#    even *that* won't kill the job.  Java code actually
#    must request the memory.

# This won't be killed
hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.job.name="Sleep without init array" \
        -Dmapreduce.map.memory.mb=256 \
        -Dmapreduce.map.java.opts=-Xms1024m \
        -DinitBigArray=false \
        -m 1 -r 1 -mt 1000

# This won't crash either
hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.job.name="Sleep, but init smallish array" \
        -Dmapreduce.map.memory.mb=256 \
        -Dmapreduce.map.java.opts=-Xms1024m \
        -DinitBigArray=true \
        -DbigArraySize=1000000 \
        -m 1 -r 1 -mt 1000

# This will crash after overstepping 256M container limit?
hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.job.name="Sleep, but init big array" \
        -Dmapreduce.map.memory.mb=256 \
        -Dmapreduce.map.java.opts=-Xms1024m \
        -DinitBigArray=true \
        -DbigArraySize=256000000 \
        -m 1 -r 1 -mt 1000

# This will NOT crash because we bumped the container size
hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.job.name="Sleep, but init big array" \
        -Dmapreduce.map.memory.mb=512 \
        -Dmapreduce.map.java.opts=-Xms1024m \
        -DinitBigArray=true \
        -DbigArraySize=256000000 \
        -m 1 -r 1 -mt 1000
