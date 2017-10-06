// Examples of table stats


In Hive / Impala, run the following:

    CREATE TABLE accounts2
    STORED AS PARQUET
    AS SELECT * FROM accounts;

In SparkSQL /  Spark Shell, run the following

    spark.sql("describe extended accounts2").select("col_name", "data_type").collect().foreach(println)

In Hive / Impala, run the following:
    
    COMPUTE STATS accounts2;

Re-run this statement, notice that Spark SQL picks up the table stats (albeit very course stats like totalSize)
    spark.sql("describe extended accounts2").select("col_name", "data_type").collect().foreach(println)

