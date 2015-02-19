#!/bin/bash
DATA_DIRECTORY=$1
test -z "$DATA_DIRECTORY" && {
        echo "Usage:  run.sh <directory in HDFS that has words to count>"
        exit 1
}

hadoop fs -test -e $DATA_DIRECTORY || {
        echo "HDFS directory $DATA_DIRECTORY doesn't exist"
        echo "Usage:  run.sh <directory in HDFS that has words to count>"
        exit 1
}
echo "RUNNING HIVE QUERY"
hive -S -d input_directory=$DATA_DIRECTORY -f ./wordcount.hql

