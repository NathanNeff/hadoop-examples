Query: select max(ss_coupon_amt) FROM tpcds.store_sales LIMIT 10
+--------------------+
| max(ss_coupon_amt) |
+--------------------+
| 19225              |
+--------------------+
Returned 1 row(s) in 467.99s

real    7m48.449s
user    0m0.835s
sys     0m0.230s

-- Parquet
Query: select max(ss_coupon_amt) FROM tpcds.parquet_store_sales LIMIT 10
+--------------------+
| max(ss_coupon_amt) |
+--------------------+
| 19225              |
+--------------------+
Returned 1 row(s) in 7.44s
real    0m7.869s
user    0m0.430s
sys     0m0.057s
