#!/bin/bash
set -e
IMPALAD=$1
test -z "$IMPALAD" && {
        echo "Usage: $0 <impala daemon>"
        exit 1
}

echo "This will drop and recreate parquet, sequencefile, rcfile data!. Press ENTER to continue, Ctrl-C to cancel"
read GOAHEAD

# Display commands before being run
set -x

# Zap tables prior to running.  There should be a tpcds_sample database with store_sales table in it.
hive -f ./drop_tables.sql
# First, create the parquet table!
impala-shell --impalad $IMPALAD --refresh_after_connect -f ./create_and_populate_parquet_table.sql

# Then, use Impala to cheat and easily create/define the RC Table Definition
# I haven't found out how to use the CREATE TABLE LIKE in Hive with RCFilez
impala-shell --impalad $IMPALAD --refresh_after_connect -f ./create_rc_and_sequencefile_table.sql

# Now, Use Hive to populate RC and SequenceFile Tables.  Impala can't do that yet.
# http://www.cloudera.com/content/cloudera-content/cloudera-docs/Impala/latest/Installing-and-Using-Impala/ciiu_file_formats.html
hive -f ./populate_rc_and_sequencefile_table.sql
