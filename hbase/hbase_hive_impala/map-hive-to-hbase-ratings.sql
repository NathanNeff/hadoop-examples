-- You may need to set hbase.zookeeper.quorum
set hbase.zookeeper.quorum=

-- The following JARs may be necessary, and distro/version dependent
-- hbase-0.94.6-<distro-version>-security.jar;
-- hive-hbase-handler-0.10.0-<distro version>.jar;

CREATE EXTERNAL TABLE IF NOT EXISTS hbase_ratings
(userid int, ratings MAP<STRING, STRING>, lname STRING)   
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'   
        WITH SERDEPROPERTIES ("hbase.columns.mapping" =":key, ratings:, info:lname")
        TBLPROPERTIES ("hbase.table.name" = "user");

-- Find ratings for movie ID 400
SELECT userid, ratings['2997'] FROM hbase_ratings WHERE ratings['2997'] IS NOT NULL;

CREATE TABLE IF NOT EXISTS exported_hbase_ratings(userid int, movieid int, rating int)
