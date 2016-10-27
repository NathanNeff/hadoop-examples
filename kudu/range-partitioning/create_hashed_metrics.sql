DROP TABLE IF EXISTS kudu_logs;
DROP TABLE IF EXISTS logs;

CREATE EXTERNAL TABLE logs
(time BIGINT, 
 metric STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LOCATION '/user/training/logs';

CREATE TABLE kudu_logs
DISTRIBUTE BY HASH(time) INTO 5 BUCKETS, RANGE(metric)
SPLIT ROWS(
  ('1')
)
TBLPROPERTIES(
    'storage_handler' = 'com.cloudera.kudu.hive.KuduStorageHandler',
    'kudu.table_name' = 'kudu_logs',
    'kudu.master_addresses' = 'localhost:7051',
    'kudu.key_columns' = 'time, metric')
AS SELECT * FROM logs;
