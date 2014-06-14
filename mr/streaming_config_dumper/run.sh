#!/bin/bash
# Purpose of this is to collect the ENV stuff from all map tasks
# shows a lot of the cool stuff that Hadoop gives to streaming tasks
MAPPER=mapper.pl
REDUCER=reducer.pl
OUTPUT_DIR=output/nothing
INPUT=something.txt

hadoop fs -test -e /user/training/something.txt || hadoop fs -put something.txt
hadoop fs -rm -R $OUTPUT_DIR

hadoop jar  /usr/lib/hadoop-0.20-mapreduce/contrib/streaming/hadoop-streaming-*.jar \
        -D com.example.something=hello \
        -input $INPUT -output $OUTPUT_DIR \
        -mapper $MAPPER -file $MAPPER \
        -reducer $REDUCER -file $REDUCER
