#!/bin/bash -e
hdfs dfs -mkdir -p /loudacre
hdfs dfs -put -f branch_totals_monday.txt /loudacre
hdfs dfs -put -f branch_totals_tuesday.txt /loudacre
beeline -u jdbc:hive2://localhost:10000 \
-f dyn_part.sql \
--silent=true \
--hiveconf hive.exec.dynamic.partition=true \
--hiveconf hive.exec.dynamic.partition.mode=nonstrict 


