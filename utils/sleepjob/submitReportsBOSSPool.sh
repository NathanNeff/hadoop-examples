#!/bin/bash
for month in January February March April May; do
        hadoop jar ~/assets/sleep.jar SleepJob \
        -D pool.name="boss" \
        -D mapred.job.name="FIFO $month" \
        -m 10 -r 10 -mt 30000 -rt 30000 &
        sleep 2
done
#-fs hdfs://greg:8020 \
#-jt hari:8021 \
