"""
Simple example of Spark Structured Streaming's rate source

References:  Changing a column's name:
  - https://stackoverflow.com/questions/34077353
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

# Remove all columns except "timestamp" and rename
renamedQuery = rateDF.selectExpr("timestamp AS ts").\
    writeStream.\
    format("console").option("truncate", False).start()

# Call rateQuery.stop()
# Call renamedQuery.stop()
