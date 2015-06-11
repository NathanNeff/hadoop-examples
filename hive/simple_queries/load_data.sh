#!/bin/bash
set -e
hive -f create_tables.sql
hdfs dfs -put -f customers.txt /user/training
hdfs dfs -put -f orders.txt /user/training
hive -e "LOAD DATA INPATH '/user/training/customers.txt' OVERWRITE INTO TABLE he.customers"
hive -e "LOAD DATA INPATH '/user/training/orders.txt' OVERWRITE INTO TABLE he.orders"
