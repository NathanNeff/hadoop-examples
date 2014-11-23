CREATE DATABASE IF NOT EXISTS hadoop_examples;

USE hadoop_examples;

DROP TABLE IF EXISTS wordcount_input;
DROP TABLE IF EXISTS wordcount_results;

CREATE EXTERNAL TABLE wordcount_input (line STRING)
LOCATION '${hivevar:input_directory}';

CREATE TABLE wordcount_results AS
SELECT word, count(1) AS count 
FROM
	(SELECT explode(split(lcase(line), '\\W+')) AS word 
          FROM wordcount_input) words
GROUP BY word ORDER BY word;
