CREATE TABLE test_decimal
(userid STRING,
 some_number DECIMAL);

CREATE EXTERNAL TABLE test_integer
(userid STRING,
 some_number INT)
LOCATION '/user/hive/warehouse/test_decimal';

LOAD DATA INPATH data.txt INTO TABLE test_decimal;
INVALIDATE METDATA;



