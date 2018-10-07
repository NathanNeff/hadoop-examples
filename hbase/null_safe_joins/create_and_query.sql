-- HBase table and data inserts

-- create 'hbase_a', 'cf1'
-- create 'hbase_b', 'cf1'
-- put 'hbase_a', '1', 'cf1:record_id', '1'
-- put 'hbase_a', '1', 'cf1:record_name', 'bob'
-- put 'hbase_b', '1', 'cf1:record_id', '1'
-- put 'hbase_b', '1', 'cf1:record_name', 'B robert'
-- # record_id for bob in both tables where record_id is NULL
-- put 'hbase_a', '2', 'cf1:record_name', 'steve'
-- put 'hbase_b', '2', 'cf1:record_name', 'steves record'

-- These create statement will map to the underlying
-- hbase_a and hbase_b tables.  the_key, record_id, and
-- record_name fields are mapped correctly.

-- drop table if exists hbase_a;
create external table hbase_a
(the_key string,
    record_id string,
    record_name string)
STORED BY
   'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping" =
        ":key, 
        cf1:record_id,
        cf1:record_name")
TBLPROPERTIES
        ("hbase.table.name" = "hbase_a");

-- drop table if exists hbase_b;
create external table hbase_b
(the_key string,
    record_id string,
    record_name string)
STORED BY
   'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping" =
        ":key, 
        cf1:record_id,
        cf1:record_name")
TBLPROPERTIES
        ("hbase.table.name" = "hbase_b");

select * from hbase_a
join hbase_b
on hbase_a.record_id <=> hbase_b.record_id;
-- Yields two rows (expected behavior)

select hbase_a.record_id, hbase_b.record_id, 
       hbase_a.record_name, hbase_b.record_name 
from hbase_a
join hbase_b
on hbase_a.record_id <=> hbase_b.record_id;
-- Yields two rows (expected behavior)

select hbase_a.record_id, hbase_b.record_id
from hbase_a
join hbase_b
on hbase_a.record_id <=> hbase_b.record_id;
-- Yields only ONE row (NOT expected behavior)

select hbase_a.record_id, hbase_b.record_id, hbase_a.record_name
from hbase_a
join hbase_b
on hbase_a.record_id <=> hbase_b.record_id;
-- Yields only one row (NOT expected behavior)

select hbase_a.record_id, hbase_b.record_id, hbase_b.record_name
from hbase_a
join hbase_b
on hbase_a.record_id <=> hbase_b.record_id;
-- Yields only one row (NOT expected behavior)
