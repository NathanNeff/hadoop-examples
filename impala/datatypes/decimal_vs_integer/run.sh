IMPALAD=$1
hdfs dfs -put data.txt
impala-shell -i $IMPALAD -f ./create_table.sql
impala-shell -i $IMPALAD -f ./run_queries.sql
