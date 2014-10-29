#!/bin/bash
set -e
IMPALAD1=$1
IMPALAD2=$2
DBNAME=hadoop_examples
TBLNAME=refresh_test

if [[ -z "$IMPALAD1" || -z "$IMPALAD2" ]]; then
        echo "run.sh <impalad1> <impalad2>"
        exit 1
fi

# Using Hive, otherwise Impala doesn't drop existing files :-O
hive -S -f ./create-table.sql
hive -S -e "LOAD DATA LOCAL INPATH 'monday.txt' INTO TABLE $DBNAME.$TBLNAME"

echo "Issuing query to $IMPALAD1 for # of rows"
impala-shell --quiet --impalad $IMPALAD1 -q "SELECT COUNT(*) FROM $DBNAME.$TBLNAME"

echo "Issuing query to $IMPALAD2 for # of rows"
impala-shell --quiet --impalad $IMPALAD2 -q "SELECT COUNT(*) FROM $DBNAME.$TBLNAME"

echo "Loading more data, but NO refresh"
hive -S -e "LOAD DATA LOCAL INPATH 'tuesday.txt' INTO TABLE $DBNAME.$TBLNAME"

echo "Issuing query to $IMPALAD1 for # of rows, should still see only 10"
impala-shell --quiet --impalad $IMPALAD1 -q "SELECT COUNT(*) FROM $DBNAME.$TBLNAME"

echo "Issuing REFRESH to $IMPALAD1 for # of rows, should now see 20"
impala-shell --quiet --impalad $IMPALAD1 -q "REFRESH $DBNAME.$TBLNAME;SELECT COUNT(*) FROM $DBNAME.$TBLNAME"

echo "Issuing count query to $IMPALAD2, withOUT refresh, should see 20 due to catalog server caching"
impala-shell --quiet --impalad $IMPALAD2 -q "SELECT COUNT(*) FROM $DBNAME.$TBLNAME"
