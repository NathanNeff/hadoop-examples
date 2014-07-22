#!/bin/bash
# Put some data into HDFS and run Pig locally, but refer to data in HDFS
hadoop fs -put -f somedata.txt THISISINHDFS.txt
pig -jt local read_some_hdfs_data.pig
