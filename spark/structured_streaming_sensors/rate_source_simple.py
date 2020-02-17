"""
Simple example of Spark Structured Streaming's rate source
"""
from pyspark.sql import SparkSession

spark = SparkSession.builder.getOrCreate()

# read data from a set of streaming files
rateDF = spark.readStream.format("rate").option("rowsPerSecond", 50).load()

# rateDF.printSchema()
# root
#  |-- timestamp: timestamp (nullable = true)
#  |-- value: long (nullable = true)

rateQuery = rateDF.writeStream.format("console").option("truncate", False).start()

# Call rateQuery.stop()
