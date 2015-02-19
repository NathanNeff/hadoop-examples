#!/bin/bash
IMPALAD=$1
DB=$2
if [[ -z "$IMPALAD" ]]; then
        echo "Usage $0 <impala daemon> <database>"
        exit 1
fi
if [[ -z "$DB" ]]; then
        echo "Usage $0 <impala daemon> <database>"
        exit 1
fi

impala-shell --database=$DB --impalad=$IMPALAD -q "alter table big_tpcds_parquet.store_sales set cached in 'four_gig_pool';"
# impala-shell --database=$DB --impalad=$IMPALAD -q "COMPUTE STATS store_sales;"
impala-shell --database=$DB --impalad=$IMPALAD --query_file=./q19.sql
# Don't run this vvvvvvvvvv
# impala-shell --big_tpcds --impalad $IMPALAD -q "INVALIDATE METADATA;"
