# Spark and HDFS Partitions
# Goal:  Find a reasonable partition key for our account devices
#        Reasonable partition keys are at least 1-2 GB per partition (HDFS)
#        Too many partitions and too few partitions are generally to be avoided

accountDeviceDF = spark.read.option("header","true") \
  .option("inferSchema","true").csv("/devsh_loudacre/accountdevice")

accountDeviceDF.show()
accountDeviceDF.printSchema()
accountDeviceDF.createOrReplaceTempView("account_devices")

#   +-----+----------+---------+---------------+--------------------+
#   |   id|account_id|device_id|activation_date|   account_device_id|
#   +-----+----------+---------+---------------+--------------------+
#   |48692|     32443|       29|  1393242509000|7351fed1-f344-4cd...|
#   |48693|     32444|        4|  1353649861000|6da22278-ff7a-461...|
#   |48694|     32445|        9|  1331819465000|cb993b85-6775-407...|

# Suggested partition key:  YYYY-MM of activation_date
# Why?  Our users are querying activations by YYYY or YYYY-MM
# A good field to partition on is (perhaps) YYYY-MM of the activation_date field
# We need to convert the activation date into a timestamp to get the YYYY-MM
# It\'s in milliseconds, so we need to divide by 1000
# Also, "device_id" is a terrible name for the field.  It's a "device_model_id".

accountDeviceBetterDF = spark.sql("""SELECT id, account_id, 
             device_id AS device_model_id,
             activation_date, 
             account_device_id,
             from_unixtime(activation_date / 1000) AS activation_timestamp,
             date_format(from_unixtime(activation_date / 1000), "YYYY-MM") AS year_month
             FROM account_devices""")
accountDeviceBetterDF.show() 
accountDeviceBetterDF.createOrReplaceTempView("account_devices_better")

# A reasonable method for storing partition METAdata is to use "Hive" tables
# We need to specify a PARTITION key, indicated by the PARTITIONED BY clause
# in the SQL below.

spark.sql("DROP TABLE IF EXISTS account_devices_partitioned")

createTableSQL = """
CREATE EXTERNAL TABLE account_devices_partitioned
( id LONG, account_id LONG, 
  device_model_id LONG,
  activation_date LONG, 
  account_device_id STRING,
  activation_timestamp TIMESTAMP)
PARTITIONED BY (year_month STRING)
LOCATION '/devsh_loudacre/account_devices_partitioned'
STORED AS PARQUET
"""

spark.sql(createTableSQL)
# You can now view the table definition using Hue

# Create a SQL statement to insert the data.
# We are using SQL to do this INSERT.  We could use
# dataWeWillInsertDF.saveAsTable() but I prefer the SQL.
# It works the same under the hood.
# Make sure the fields we select are in the same order
# 
# Spark partitions the data according to the LAST field specified!!!!

populateAccountDevicesSQL = """
INSERT INTO account_devices_partitioned
PARTITION(year_month)
SELECT id, account_id, 
       device_model_id, 
       activation_date, 
       account_device_id,
       activation_timestamp,
       year_month
FROM account_devices_better
"""

# We need this annoying configuration because Spark/Hive wants to prevent
# "runaway" dynamic partitioning of data by default
spark.conf.set("hive.exec.dynamic.partition.mode", "nonstrict")
spark.sql(populateAccountDevicesSQL)

# View the data in HDFS we will need to run this in a %sh paragraph (Or, use Hue to
# browse this directory: /devsh_loudacre/account_devices_partitioned
# hdfs dfs -ls -R /devsh_loudacre/account_devices_partitioned

# Query our data
spark.sparkContext.setJobGroup("Partitions", "Query Activations from 2014-12")
df2012_14 = spark.sql("select * from account_devices_partitioned where year_month = '2014-12'")
df2012_14.count()
# View the Spark Job in the Spark UI.  Identify that Partition Pruning is happening
spark.sparkContext.setJobGroup("Partitions", "Query Activations from 2012")
df2012 = spark.sql("select * from account_devices_partitioned where year_month LIKE '2012%'")
df2012.count()

