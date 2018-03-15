#!/bin/bash -e
hdfs dfs -mkdir -p /loudacre
hdfs dfs -put -f branch_totals_monday.txt /loudacre
hdfs dfs -put -f branch_totals_tuesday.txt /loudacre
impala-shell -f dyn_part.sql --quiet
