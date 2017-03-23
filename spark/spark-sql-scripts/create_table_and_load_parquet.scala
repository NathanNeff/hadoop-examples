// This is meant to be run inside the Spark Shell
import sqlContext.implicits._
import org.apache.spark.sql._

val tableName = "hadoop_examples_ips_parquet"

// don't forget the "s" function before """
sqlContext.sql(s"""CREATE EXTERNAL TABLE IF NOT EXISTS $tableName
                  (ip STRING)
                  STORED AS PARQUET""")

val ips_schema = sqlContext.table(tableName).schema

val data = Array("123.456.789.999 - bob - GET /cat_picture.jpg",
                 "1.2.3.4 - steve GET /dog_picture.jpg")

val weblogs = sc.parallelize(data)

val ips = weblogs.map(line => Row(line.split(' ')(0)))

val ips_df = sqlContext.createDataFrame(ips, ips_schema)

ips_df.insertInto(tableName)
