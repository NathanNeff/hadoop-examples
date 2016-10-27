-- NOTE:  First mkdir /user/training/people then
-- hdfs dfs -put people.txt /user/training/people
DROP TABLE IF EXISTS kudu_people;
DROP TABLE IF EXISTS people;

CREATE EXTERNAL TABLE people
(name STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LOCATION '/user/training/people';

CREATE TABLE kudu_people
DISTRIBUTE BY RANGE(name)
SPLIT ROWS(
("a"),
("b"),
("c"),
("d"),
("e"),
("f"),
("g"),
("h"),
("i"),
("j"),
("k"),
("l"),
("m"),
("n"),
("o"),
("p"),
("q"),
("r"),
("s"),
("t"),
("u"),
("v"),
("w"),
("x"),
("y"),
("z")) 
TBLPROPERTIES(
	'storage_handler' = 'com.cloudera.kudu.hive.KuduStorageHandler',
    'kudu.table_name' = 'kudu_people',
    'kudu.master_addresses' = 'localhost:7051',
    'kudu.key_columns' = 'name')
AS SELECT * FROM people;
