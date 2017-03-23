// This is meant to be run inside the Spark Shell
import sqlContext.implicits._
import org.apache.spark.sql._

sqlContext.sql("""CREATE EXTERNAL TABLE IF NOT EXISTS hadoop_examples_ips
                  (ip STRING)
                  ROW FORMAT DELIMITED 
                  FIELDS TERMINATED BY '\t'""")
val ips_schema = sqlContext.table("hadoop_examples_ips").schema

val data = Array("123.456.789.999 - bob - GET /cat_picture.jpg",
                 "1.2.3.4 - steve GET /dog_picture.jpg")

val weblogs = sc.parallelize(data)

val ips = weblogs.map(line => Row(line.split(' ')(0)))

val ips_df = sqlContext.createDataFrame(ips, ips_schema)

ips_df.insertInto("hadoop_examples_ips")
