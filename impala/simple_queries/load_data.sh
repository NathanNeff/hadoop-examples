#!/bin/bash
set -e
impala-shell -f create_tables.sql
hdfs dfs -put -f customers.txt /user/training
hdfs dfs -put -f orders.txt /user/training
impala-shell -q "LOAD DATA INPATH '/user/training/customers.txt' OVERWRITE INTO TABLE he.customers"
impala-shell -q "LOAD DATA INPATH '/user/training/orders.txt' OVERWRITE INTO TABLE he.orders"
