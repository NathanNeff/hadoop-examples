DROP TABLE IF EXISTS branch_totals;
DROP TABLE IF EXISTS monday_totals;
DROP TABLE IF EXISTS tuesday_totals;

CREATE TABLE monday_totals(
	year INT,
    branch STRING,
    total INT
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t';

LOAD DATA INPATH '/loudacre/branch_totals_monday.txt'
INTO TABLE monday_totals;

CREATE TABLE tuesday_totals LIKE monday_totals;

LOAD DATA INPATH '/loudacre/branch_totals_tuesday.txt'
INTO TABLE tuesday_totals;
	
CREATE TABLE branch_totals(
branch STRING,
total INT)
PARTITIONED BY (year INT)
STORED AS PARQUET;

INSERT OVERWRITE TABLE branch_totals
PARTITION(year)
SELECT branch, total, year
FROM monday_totals;

SELECT year, branch, total 
FROM branch_totals
ORDER BY year;

INSERT OVERWRITE TABLE branch_totals
PARTITION(year)
SELECT branch, total, year
FROM tuesday_totals;

SELECT year, branch, total 
FROM branch_totals
ORDER BY year;
