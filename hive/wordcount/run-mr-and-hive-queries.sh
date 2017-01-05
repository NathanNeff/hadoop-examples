#!/bin/bash
SHAKESPEARE_DIR=/user/training/shakespeare
LOCAL_DATA_DIR=~/training_materials/developer/data/
OUTPUT_DIR=/user/training/output/wordcount

# Already set in ~/.bashrc
# EXAMPLES_DIR=/usr/lib/hadoop-0.20-mapreduce

hadoop fs -test -d $SHAKESPEARE_DIR || {

	test -d $LOCAL_DATA_DIR/shakespeare || \
		tar -C $LOCAL_DATA_DIR -xzvf $LOCAL_DATA_DIR/shakespeare.tar.gz

	hadoop fs -put $LOCAL_DATA_DIR/shakespeare $SHAKESPEARE_DIR
}
	
echo "RUNNING MAPREDUCE JOB"

hadoop fs -rm -R $OUTPUT_DIR
hadoop jar $EXAMPLES_DIR/hadoop-examples.jar wordcount $SHAKESPEARE_DIR \
	$OUTPUT_DIR

echo "RUNNING HIVE QUERY"
hive -f ./wordcount.hql

