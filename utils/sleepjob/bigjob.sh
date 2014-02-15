#!/bin/bash
OUTPUT_DIR=/user/training/nate/output/wordcount2
INPUT_DIR=/user/training/nate/input
hadoop fs -test -d $OUTPUT_DIR && hadoop fs -rm -R $OUTPUT_DIR
hadoop jar /usr/lib/hadoop-0.20-mapreduce/hadoop-examples.jar wordcount $INPUT_DIR $OUTPUT_DIR
