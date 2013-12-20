-- What if you want to keep the original data (state)
-- in the partitioned table?  Then create a dummy field
-- in the original table, and select the "state" field twice
-- below.
DROP TABLE IF EXISTS db1.employees_partitioned_keep_orig_data;
CREATE TABLE db1.employees_partitioned_keep_orig_data(name STRING, orig_state STRING)
    PARTITIONED BY (state STRING)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY '\t';

set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;
INSERT OVERWRITE TABLE db1.employees_partitioned_keep_orig_data
    PARTITION (state)
    SELECT name, state AS orig_state, state FROM db1.employees;
