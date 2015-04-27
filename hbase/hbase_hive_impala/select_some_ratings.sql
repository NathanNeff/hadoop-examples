-- These add jar statements will vary, depending on distro
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

-- SELECT * FROM the_movie_ratings WHERE movie_ratings['star_wars'] IS NOT NULL;

-- Find top 2 users with the most movie ratings
SELECT userid, MAP_KEYS(movie_ratings) AS the_count
FROM the_movie_ratings;

-- WHERE movie_ratings['star_wars'] IS NOT NULL
-- GROUP BY the_count
-- ORDER BY the_count DESC
-- LIMIT 2;

