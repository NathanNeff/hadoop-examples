# README

Illustration of variations in the SQL-dialects
on a given version of Hadoop/Impala.

To load data from ./accts.txt

    ./load_data.sh <database> <userid> <password>

To compare SQL variations, look at query*.sql files, then run
them using:

    ./run_queries.sh <database> <userid> <password>
    
To compare output, review output files:

    ./query1.sql-hive-results.txt vs.
    ./query1.sql-impala-results.txt vs.
    ./query1.sql-mysql-results.txt

You could use some diff script or just run diff.
