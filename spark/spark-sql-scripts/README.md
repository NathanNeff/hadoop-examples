# spark-sql-scripts

This directory contains simple Spark SQL snippets
that are meant to be run from a Spark Shell that
already has a sqlContext initialized.

# create_table_and_load.scala

Creates a DataFrame from an RDD using schema from
an existing table.

- TODO Need to investigate deprecation warning [1] (potentially from
       insertInto function?) and need to investigate KeyProviderCache exception [2]

       However, code still runs "correctly" and inserts into table.

[1] warning: there were 1 deprecation warning(s); re-run with -deprecation for details

[2] ERROR hdfs.KeyProviderCache: Could not find uri with key [dfs.encryption.key.provider.uri] 
    to create a keyProvider !!

