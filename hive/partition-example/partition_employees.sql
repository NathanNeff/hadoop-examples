DROP TABLE IF EXISTS db1.employees_partitioned;
CREATE TABLE db1.employees_partitioned(name STRING)
    PARTITIONED BY (state STRING)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY '\t';

set hive.exec.dynamic.partition=true;
set hive.exec.dynamic.partition.mode=nonstrict;
-- The columns you're partitioning by should be listed at the END of the SELECT statement
INSERT OVERWRITE TABLE db1.employees_partitioned 
    PARTITION (state)
    SELECT name, state FROM db1.employees;
