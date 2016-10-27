hdfs dfs -mkdir -p /user/training/people
hdfs dfs -rm /user/training/people/*
hdfs dfs -put ./people.txt /user/training/people
impala-shell -f ./create_people.sql
