#!/bin/bash
STREAMING_JAR=/opt/cloudera/parcels/CDH/lib/hadoop-mapreduce/hadoop-streaming.jar
MAPPER=mapper.pl
OUTPUT_DIR=output/map_only_streaming
INPUT=tpcds_data.dat

hadoop fs -rm -R $OUTPUT_DIR

hadoop jar $STREAMING_JAR \
        -D mapred.map.tasks=20 \
        -D mapred.reduce.tasks=0 \
        -input $INPUT -output $OUTPUT_DIR \
        -mapper $MAPPER -file $MAPPER
