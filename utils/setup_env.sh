#!/bin/bash
for dir in $EXAMPLES_DIR /opt/cloudera/parcels/CDH/lib/hadoop-0.20-mapreduce
        do
                test -d $dir && export EXAMPLES_DIR=$dir
        done

test -d $EXAMPLES_DIR || {
        echo "Can't find examples dir $EXAMPLES_DIR"
        exit 1
}

export EXAMPLES_JAR=$EXAMPLES_DIR/hadoop-examples.jar

test -f $EXAMPLES_JAR || {
        echo "Can't find $EXAMPLES_JAR"
        exit 1
}
