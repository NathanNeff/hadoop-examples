#!/bin/bash
# Test how many times a naughty process is retried when it violates the container's memory size
# NOTE: Make sure that yarn.scheduler.minimum-allocation-mb is below mapreduce.map.memory.mb
#       or the container memory won't matter.
#       Also, make sure the nodes you're running this on have enough memory and don't swap, or
#       you might have these jobs fail simply because they're forcing swap
set -e
rm -f *.class
javac -cp `hadoop classpath` ./SleepJobWithArray.java
jar cvf SleepJobWithArray.jar *.class


# Max container size is 256 MB, but Java heap is 1024.
#    even *that* won't kill the job.  Java code actually
#    must request the memory.

# This won't be killed because we don't allocate the array
# Note that it doesn't matter about Xms being 1024m (greater than container size)
hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.job.name="Sleep without init array" \
        -Dmapreduce.map.memory.mb=256 \
        -Dmapreduce.map.java.opts=-Xmx200m \
        -DinitBigArray=false \
        -m 1 -r 1 -mt 10000 &

# This won't crash because it's only using a small array
# "C" is container size, "A" is array size
false && hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.job.name="Sleep: C=256 A=10" \
        -Dmapreduce.map.memory.mb=256 \
        -Dmapreduce.map.java.opts=-Xms1024m \
        -DinitBigArray=true \
        -DbigArraySize=10000000 \
        -m 1 -r 1 -mt 10000 &

# This will crash after overstepping 256M container limit
# "C" is container size, "A" is array size
false && hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.job.name="Sleep: C=256, A=100" \
        -Dmapreduce.map.memory.mb=256 \
        -Dmapreduce.map.java.opts=-Xms1024m \
        -DinitBigArray=true \
        -DbigArraySize=100000000 \
        -m 1 -r 1 -mt 10000 &

# This will NOT crash because we bumped the container size
# Note how the Xms doesn't affect any job
# "C" is container size, "A" is array size
false && hadoop jar ./SleepJobWithArray.jar SleepJobWithArray \
        -Dmapreduce.job.name="Sleep: C=512 A=100" \
        -Dmapreduce.map.memory.mb=512 \
        -Dmapreduce.map.java.opts=-Xms1024m \
        -DinitBigArray=true \
        -DbigArraySize=100000000 \
        -m 1 -r 1 -mt 10000 &
