#!/bin/bash
INPUT_DIR=input/random-crash
OUTPUT_DIR=output/random-crash
STREAMING_JAR=/usr/lib/hadoop-0.20-mapreduce/contrib/streaming/hadoop-streaming-*.jar

hadoop fs -test -d $INPUT_DIR && hadoop fs -rm -R $INPUT_DIR
hadoop fs -test -d $OUTPUT_DIR && hadoop fs -rm -R $OUTPUT_DIR
hadoop fs -mkdir $INPUT_DIR

for i in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20;do
        hadoop fs -touchz $INPUT_DIR/$i
done

hadoop jar $STREAMING_JAR \
-Dmapred.reduce.tasks=0 \
-input $INPUT_DIR 
-output $OUTPUT_DIR \
-mapper mapper.pl 
-file mapper.pl
