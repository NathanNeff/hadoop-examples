DROP TABLE shakespeare;
CREATE EXTERNAL TABLE shakespeare (line STRING)
LOCATION '/user/training/shakespeare';

DROP TABLE IF EXISTS wordcount;
CREATE TABLE wordcount AS
SELECT word, count(1) AS count 
FROM
	(SELECT explode(split(lcase(line), '\\W+')) AS word 
          FROM shakespeare) words
GROUP BY word ORDER BY word;
