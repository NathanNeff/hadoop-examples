from pyspark.sql import SparkSession
from pyspark.sql.functions import udf
import time
import random

def main():
    # read data from a set of streaming files
    rateDF = spark.readStream.format("rate").load()
    rateQuery = rateDF.writeStream.format("console").option("truncate", False).start()
    return rateQuery

if __name__ == "__main__":
    spark = SparkSession.builder.getOrCreate()
    rateQuery = main()
