create 'hbase_a', 'cf1'
create 'hbase_b', 'cf1'

put 'hbase_a', '1', 'cf1:record_id', '1'
put 'hbase_a', '1', 'cf1:record_name', 'bob'

put 'hbase_b', '1', 'cf1:record_id', '1'
put 'hbase_b', '1', 'cf1:record_name', 'B robert'

# record_id for bob in both tables where record_id is NULL
put 'hbase_a', '2', 'cf1:record_name', 'steve'
put 'hbase_b', '2', 'cf1:record_name', 'steves record'

exit
