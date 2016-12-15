#!/bin/bash
set -e
DB_NAME=$1
USER_ID=$2
PASSWORD=$3
if [[ -z "$DB_NAME" || -z "$USER_ID" || -z "$PASSWORD" ]]; then
	echo "Usage: $0 <DB_NAME> <USER_ID> <PASSWORD>"
	exit 1
fi

# Load MySQL
mysql -u $USER_ID --password=$PASSWORD <<EOF
		CREATE DATABASE IF NOT EXISTS $DB_NAME;

		USE $DB_NAME;

		DROP TABLE IF EXISTS accts;

		CREATE TABLE accts (
		id INT,
		first_name VARCHAR(255),
		last_name VARCHAR(255),
		state VARCHAR(255));
EOF

mysqlimport -u $USER_ID --password=$PASSWORD \
   --fields-terminated-by '\t' \
   --delete $DB_NAME `pwd`/accts.txt


hdfs dfs -mkdir -p /user/$USER/input_data
hdfs dfs -put -f accts.txt /user/$USER/input_data

# Load Impala/Hive
impala-shell -q "
CREATE DATABASE IF NOT EXISTS $DB_NAME;

USE $DB_NAME;

DROP TABLE IF EXISTS accts;

CREATE TABLE accts (
		id INT,
		first_name STRING,
		last_name STRING,
		state STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t';

LOAD DATA INPATH '/user/$USER/input_data' OVERWRITE INTO TABLE accts;"

