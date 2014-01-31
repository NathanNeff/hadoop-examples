-- Use Impala to run this!
USE tpcds_sample;
CREATE TABLE parquet_store_sales 
        LIKE store_sales
        STORED AS PARQUET;

INSERT INTO parquet_store_sales SELECT * FROM store_sales;
