The script run.sh will do everything for you.

get_partition_info.sql has an example of EXPLAIN EXTENDED
to show that Hive will use Partitions in a SELECT statement.
Note that Hive is really smart, and if you SELECT * according to
a partition, Hive will not run MapReduce, it will just perform a
hadoop fs -get /user/hive/warehouse/yourtable/partition=<whatever> :)
