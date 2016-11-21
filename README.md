Hadoop Examples
===============

Hadoop Examples is a set of simple example scripts to illustrate Hadoop ecosystem
tools like Hive and Pig.

Installation
-------------
EXAMPLES_DIR is an environment variable you can set to point to the directory
where the hadoop-examples.jar is installed.

There is also a script: utils/setup_env.sh that can be sourced inside other
shell scripts to try to find the hadoop-examples.jar.  It is ugly, but
sometimes convenient :-/

# Release Notes

## HBase Block Size

November 2016

HBase Block Size utility =hbase/hbase_blocks/hbase_blocks.rb= creates a table with
a specified HBase block size.  Writes data, flushes, then uses admin object to
get the region name.  Displays exact command =hbase hfile= to use to view the store
file's index.  Some okay/kinda cool JRuby stuff there.


Streaming Config Dumper
-----------------------

MapReduce scripts to print their ENV variables, which also include
Hadoop configuration stuff for streaming jobs.

See =mr/streaming_config_dumper/=


Hive and Pig
------------
12/20/2013 

- Incremental insert example in Hive
  Inserts non-duplicate data into a join table from incrementally updated
  source tables See hive/incremental_insert/

- Added example of Pig's EXPLAIN command to show a diagram of the execution plan
             for SPLIT versus FILTER
  See pig/explain-split-vs-filter/

- Added example of Hive's PARTITION feature
  See hive/partition-example/



