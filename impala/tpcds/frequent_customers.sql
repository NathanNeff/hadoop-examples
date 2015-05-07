-- For kicks, try store_sales versus parquet_store_sales
-- For smaller data size, use tpcds_sample database
USE tpcds_parquet;
SELECT ss_customer_sk, 
        COUNT(*) AS num_purchases,
        SUM(ss_net_profit) AS total_profit
FROM store_sales
        GROUP BY ss_customer_sk
        ORDER BY num_purchases DESC
        LIMIT 100;
