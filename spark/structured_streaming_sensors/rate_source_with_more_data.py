"""
Simple example of Spark Structured Streaming's rate source
This code uses a UDF to generate fake sensor data
*NOTE* Be sure to read Jacek Laskowski's recommendations on UDFs:
https://jaceklaskowski.gitbooks.io/mastering-spark-sql/spark-sql-udfs-blackbox.html
"""
import random
from pyspark.sql           import SparkSession
from pyspark.sql.types     import StructType, StructField, IntegerType, StringType
from pyspark.sql.functions import udf

# Define a StructType to be able to return complex / multiple
# fields from a UDF call.
# Thanks to https://stackoverflow.com/questions/35322764/
# and Cloudera Developer Course:
# https://www.cloudera.com/about/training/courses/developer-training-for-spark-and-hadoop.html
SENSOR_RECORD_TYPE = StructType([
    StructField("machine", StringType()),
    StructField("sensor_id", IntegerType()),
    StructField("sensor_value", IntegerType()),
    StructField("err_code", StringType())])

NUM_MACHINES = 100

SENSORS_PER_MACHINE = 10
SENSORS = {
    "sensor_0" : [1, 5],
    "sensor_1" : [1, 20],
    "sensor_2" : [0, 5],
    "sensor_3" : [20, 60],
    "sensor_4" : [1, 60],
    "sensor_5" : [50, 100],
    "sensor_6" : [10, 100],
    "sensor_7" : [80, 150],
    "sensor_8" : [1, 50],
    "sensor_9" : [1, 10],
    "sensor_10" : [1, 10],
    "sensor_11" : [1, 10]
}

def random_sensor_reading():
    """
    Create random sensor reading from sensors.
    Inject errors (wacky values) randomly
    """
    err_code = 0
    machine = "host" + str(random.randint(1, NUM_MACHINES))
    sensor_id = random.randint(1, SENSORS_PER_MACHINE)
    min_val, max_val = SENSORS.get("sensor_" + str(sensor_id))

    inject_error = False
    if random.randint(1, 100) >= 85:
        inject_error = True

    sensor_val = random.randint(min_val, max_val)
    if inject_error and random.randint(1, 100) < 80:
        sensor_val += random.randint(500, 1000)
        err_code = random.randint(1, 10)

    return [machine, sensor_id, sensor_val, err_code]

spark = SparkSession.builder.getOrCreate()

udf_random_sensor_reading = udf(random_sensor_reading, SENSOR_RECORD_TYPE)

# read data from a set of streaming files
rateDF = spark.readStream.format("rate").option("rowsPerSecond", 50).load()
sensorDF = rateDF.withColumn("sensor_struct", udf_random_sensor_reading())
finalDF = sensorDF.select("timestamp", "sensor_struct.machine", "sensor_struct.sensor_id",
                          "sensor_struct.sensor_value", "sensor_struct.err_code")

sensorQuery = finalDF.writeStream.format("console").option("truncate", False).start()

# Call sensorQuery.stop()
