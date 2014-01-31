#!/bin/bash
# Simply a script to run some counts, selects on the various tables
IMPALAD=$1
if [[ -z "$IMPALAD" ]]; then
        echo "Usage $0 <impala daemon>"
        exit 1
fi

impala-shell --impalad $IMPALAD -q "INVALIDATE METADATA;"

for tbl in seq_store_sales parquet_store_sales rc_store_sales store_sales
do
                QUERY=$(cat <<EEYORE
                SELECT COUNT(*) FROM $tbl;
                SELECT ss_coupon_amt FROM $tbl 
                        WHERE ss_coupon_amt IS NOT NULL
                        ORDER BY ss_coupon_amt DESC
                        LIMIT 10;
EEYORE
)
                        
time impala-shell --database tpcds_sample --impalad $IMPALAD -q "$QUERY"
# --output_file $tbl.out
done
