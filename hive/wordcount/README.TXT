Basic idea:

1) Run wordcount on shakespare using MapReduce
2) Run wordcount on shakespeare using Hive and compare differences

Steps
1) Upload shakespeare to cluster
2) Run wordcount using MapReduce
3) Create a table 'mapred_wordcount' containing the output from MapReduce job

4) Load shakespeare into a Hive table
5) Run Hive Query to create a hive_wordcount table
6) Compare hive_wordcount to mapred_wordcount








First run ./run-comparison.sh
Then you can use ./create-external-table-for-mapreduce-output.hql
to view the output of the mapreduce job more easily,
or you can simply use 

hadoop fs -get output/wordcount
