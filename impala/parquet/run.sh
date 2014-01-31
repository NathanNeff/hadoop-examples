IMPALAD=$1
if [[ -z "$IMPALAD" ]]; then
        echo "Usage: run.sh <impalad>"
        exit 1
fi
echo "Running against Parquet table......"
time impala-shell --impalad training01 -q "select ss_coupon_amt FROM tpcds.parquet_store_sales WHERE ss_coupon_amt IS NOT NULL ORDER BY ss_coupon_amt DESC LIMIT 10;"
echo "Running against Text table......"
time impala-shell --impalad training01 -q "select ss_coupon_amt FROM tpcds.store_sales WHERE ss_coupon_amt IS NOT NULL ORDER BY ss_coupon_amt DESC LIMIT 10;"
