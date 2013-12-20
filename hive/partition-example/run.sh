#!/bin/sh
# Script should stop if there's a failure
set -e
hive -S -f create_and_load_employees.sql
hive -S -f partition_employees.sql
hive -S -f partition_employees_keep_orig_data.sql
echo "Browse the data in the /user/hive/warehouse/db1 directory"
hive -S -v -f get_partition_info.sql
