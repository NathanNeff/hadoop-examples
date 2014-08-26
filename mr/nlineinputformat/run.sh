#!/bin/bash
set -e
OUTPUT_DIR=output/nlineinputformat_output
STREAMING_JAR=/opt/cloudera/parcels/CDH/lib/hadoop-mapreduce/hadoop-streaming.jar 
INPUT_DIR=nlineinputformat_example2
INPUT_FILE=task_list.txt
        
hadoop fs -test -d $INPUT_DIR || hadoop fs -mkdir $INPUT_DIR
hadoop fs -test -d $OUTPUT_DIR && hadoop fs -rm -R $OUTPUT_DIR

# Force putting input file into HDFS every time from local dir
hadoop fs -put -f $INPUT_FILE $INPUT_DIR

hadoop jar $STREAMING_JAR \
        -D mapreduce.input.lineinputformat.linespermap=1 \
        -mapper mapper.pl \
        -file mapper.pl \
        -input $INPUT_DIR \
        -inputformat org.apache.hadoop.mapred.lib.NLineInputFormat \
        -output $OUTPUT_DIR

exit 0
# Don't need this syntax anymore
# -D mapred.reduce.tasks=0 \
# Usage: $HADOOP_PREFIX/bin/hadoop jar hadoop-streaming.jar [options]
# Options:
#   -input          <path> DFS input file(s) for the Map step.
#   -output         <path> DFS output directory for the Reduce step.
#   -mapper         <cmd|JavaClassName> Optional. Command to be run as mapper.
#   -combiner       <cmd|JavaClassName> Optional. Command to be run as combiner.
#   -reducer        <cmd|JavaClassName> Optional. Command to be run as reducer.
#   -file           <file> Optional. File/dir to be shipped in the Job jar file.
#                   Deprecated. Use generic option "-files" instead.
#   -inputformat    <TextInputFormat(default)|SequenceFileAsTextInputFormat|JavaClassName>
#                   Optional. The input format class.
#   -outputformat   <TextOutputFormat(default)|JavaClassName>
#                   Optional. The output format class.
#   -partitioner    <JavaClassName>  Optional. The partitioner class.
#   -numReduceTasks <num> Optional. Number of reduce tasks.
#   -inputreader    <spec> Optional. Input recordreader spec.
#   -cmdenv         <n>=<v> Optional. Pass env.var to streaming commands.
#   -mapdebug       <cmd> Optional. To run this script when a map task fails.
#   -reducedebug    <cmd> Optional. To run this script when a reduce task fails.
#   -io             <identifier> Optional. Format to use for input to and output
#                   from mapper/reducer commands
#   -lazyOutput     Optional. Lazily create Output.
#   -background     Optional. Submit the job and don't wait till it completes.
#   -verbose        Optional. Print verbose output.
#   -info           Optional. Print detailed usage.
#   -help           Optional. Print help message.
