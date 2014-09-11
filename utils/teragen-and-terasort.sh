#!/bin/bash
# Usage is here:
# http://www.michael-noll.com/blog/2011/04/09/benchmarking-and-stress-testing-an-hadoop-cluster-with-terasort-testdfsio-nnbench-mrbench/#teragen-generate-the-terasort-input-data-if-needed
INPUT_DIR=data/teragendata_10_gb
OUTPUT_DIR=output/terasort_10_gb

# 1 GB = 10000000 
# 20 GB = 200000000 
TERAGEN_SIZE=100000000 
# Try Bumping # of Maps for Teragen :-)
TERAGEN_MAPS=20
FORCE_TERAGEN=1
# Set RUN_COMPARISON to 1 if you want to run terasort (again) with io.sort.mb set differently
RUN_COMPARISON=1
OUTPUT_DIR2=output/terasort10_gb_again

# If you don't have EXAMPLES_DIR set, you can manually set it here,
# or let this code loop through possible dirs and find an existing directory
test -z "$EXAMPLES_DIR" && {
        for d in /usr/lib/hadoop-0.20-mapreduce/ /opt/cloudera/parcels/CDH/lib/hadoop-0.20-mapreduce;
        do
                test -d $d && EXAMPLES_DIR=$d
        done
}

# hadoop jar hadoop-*examples*.jar teragen <number of 100-byte rows> <output dir>
if ! hadoop fs -test -e $INPUT_DIR/part-00000 || [[ "$FORCE_TERAGEN" -ne "0" ]]; then
	echo "------------- Creating test data --------------"
	hadoop fs -rm -R $INPUT_DIR
        
	hadoop jar $EXAMPLES_DIR/hadoop-examples.jar teragen -Dmapred.map.tasks=$TERAGEN_MAPS $TERAGEN_SIZE $INPUT_DIR
fi

hadoop fs -test -d $OUTPUT_DIR && hadoop fs -rm -R $OUTPUT_DIR
hadoop jar $EXAMPLES_DIR/hadoop-examples.jar terasort $INPUT_DIR $OUTPUT_DIR

if [[ "1" -eq "$RUN_COMPARISON" ]]; then
        # This might be needed if running on EC2 (Admin Class) or smaller cluster
        # with less than 1GB Heap Size for Mappers
        # -Dmapred.child.java.opts=-Xmx512m 
        hadoop jar $EXAMPLES_DIR/hadoop-examples.jar terasort -Dio.sort.mb=256 $INPUT_DIR $OUTPUT_DIR2
fi
