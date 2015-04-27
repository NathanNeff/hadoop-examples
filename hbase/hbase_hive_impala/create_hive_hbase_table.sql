-- The following JARs might need to be added.
-- Use Hive's ADD JAR command
-- zookeeper.jar;
-- hive-hbase-handler.jar;
-- guava-11.0.2.jar;
-- hbase-client.jar;
-- hbase-common.jar;
-- hbase-hadoop-compat.jar;
-- hbase-hadoop2-compat.jar;
-- hbase-protocol.jar;
-- hbase-server.jar;
-- htrace-core.jar;

DROP TABLE IF EXISTS sales_grouped;

CREATE EXTERNAL TABLE sales_grouped
        (customer_id INT, 
         total_sales INT)
STORED BY
   'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping" =
        ":key, 
        cf1:total_sales")
TBLPROPERTIES
        ("hbase.table.name" = "hbase_sales_grouped");


DROP TABLE IF EXISTS the_movie_ratings;

-- ratings is simply the entire 'ratings' column family 
CREATE EXTERNAL TABLE the_movie_ratings
        (userid STRING, 
         movie_ratings MAP<STRING,STRING>)
STORED BY
   'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping" =
        ":key, 
        ratings:") -- just map entire column family to the movie_ratings MAP
TBLPROPERTIES
        ("hbase.table.name" = "nate_hbase_movie_ratings");
