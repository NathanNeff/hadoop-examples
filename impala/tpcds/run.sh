#!/bin/bash
# 100GB dataset!
# 15 seconds with Parquet, versus 480 seconds with plain text
IMPALAD=$1
TABLE=tpcds.parquet_store_sales
if [[ -z "$IMPALAD" ]]; then
        echo "Usage:  $0 <impalad>"
        exit 1
fi

impala-shell --impalad $IMPALAD -f ./frequent_customers.sql
