#!/bin/bash
# This script runs impala and hive and MapReduce wordcount.
# Please remove whatever you don't want to run.
set -e
IMPALA_SERVER=$1
OUTPUT_DIR=output/wc_dosjunk
impala-shell -i $IMPALA_SERVER -f ./get_max.sql
hive -f ./get_max.sql
hadoop fs -test -d $OUTPUT_DIR && hadoop fs -rm -r $OUTPUT_DIR 
hadoop jar $EXAMPLES_DIR/hadoop-examples.jar wordcount dosjunk $OUTPUT_DIR 
hadoop fs -getmerge $OUTPUT_DIR wordcount_output.txt
echo "Wordcount output is in wordcount_output.txt"
