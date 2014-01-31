-- Use Impala to run this!
INVALIDATE METADATA;
USE tpcds_sample;

DROP TABLE IF EXISTS rc_store_sales;
DROP TABLE IF EXISTS seq_store_sales;

CREATE TABLE rc_store_sales 
        LIKE store_sales
        STORED AS RCFILE;

CREATE TABLE seq_store_sales 
        LIKE store_sales
        STORED AS SEQUENCEFILE;
