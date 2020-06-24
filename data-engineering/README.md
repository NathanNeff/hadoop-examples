# Comparison of Hive / Impala in CDP 7.2

https://docs.cloudera.com/runtime/7.2.0/using-hiveql/topics/hive-orc-parquet-compare.html
# Run the following in order

1) create_txnl_tbl.sh

1) insert_into_products_txnl.sh

-- Impala currently does not query fully transactional tables
-- (Coming soon)
-- https://issues.apache.org/jira/browse/IMPALA-9042

1) query_txnl_using_impala.sh

-- Hive does
1) query_txnl_using_hive.sh

-- View directory structure in table
1) ./view_dir_structure.sh

-- Update data in txnl table
1) ./update_txnl_using_hive.sh

-- View directory structure in table
1) ./view_dir_structure.sh

