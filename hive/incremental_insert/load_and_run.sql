-- This example shows how to use Hive to insert non-duplicate data
-- into a join table.

-- Employees table:
-- bob     supervisor

-- Nicknames table:
-- bob     bob_nickname

-- We create a join_table with a very simple initial
-- dataset:
-- bob     supervisor     bob_nickname     
-- steve   programmer     steve_nickname     

-- Then, we load *more* nicknames into the nicknames table, and only
-- insert the new nickname relations into the join_table.
-- bob     another_bob_nickname

-- We want the resulting join_table to include only:
-- bob     supervisor     bob_nickname     
-- bob     supervisor     another_bob_nickname
-- steve   programmer     steve_nickname     
-- steve   programmer     another_steve_nickname     

-- We don't want to get duplicate data in the join_table
ADD FILE join_table.sql;
CREATE DATABASE IF NOT EXISTS incremental_insert;

USE incremental_insert;

DROP TABLE IF EXISTS employees;
CREATE TABLE employees(name STRING, title STRING);
LOAD DATA LOCAL INPATH 'employees.txt' INTO TABLE employees;

DROP TABLE IF EXISTS nicknames;
CREATE TABLE nicknames(name STRING, nickname STRING);
LOAD DATA LOCAL INPATH 'nicknames.txt' INTO TABLE nicknames;

DROP TABLE IF EXISTS join_table;
CREATE TABLE join_table(name STRING, title STRING, nickname STRING);

-- Run the join
SOURCE join_table.sql;
SELECT COUNT(*) FROM join_table;

-- Now, load more nicknames
LOAD DATA LOCAL INPATH 'more_nicknames.txt' INTO TABLE nicknames;

-- Run the join again
SOURCE join_table.sql;
SELECT COUNT(*) FROM join_table;

