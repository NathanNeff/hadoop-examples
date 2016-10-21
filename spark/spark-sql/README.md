## Step 1: Add data to HDFS

    hdfs dfs -mkdir /user/training/
    hdfs dfs -put data/favorite_foods /user/training

## Step 2

## Step 3: Profit!

    mvn package
    spark-submit --class examples.ExplodeAndFriends target/SparkSQLExamples-1.0.jar
