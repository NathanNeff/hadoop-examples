CREATE EXTERNAL TABLE mr_wordcount
(word STRING, count INT)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LOCATION '/user/training/output/wordcount'
