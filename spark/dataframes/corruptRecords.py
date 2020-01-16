# Put data into HDFS prior to running
# hdfs dfs -put hadoop-examples-data
# For config options, see
#     https://spark.apache.org/docs/latest/api/python/pyspark.sql.html#pyspark.sql.DataFrameReader.json
from pyspark.sql.types import *
maxValsData = "hadoop-examples-data/maxVals.json"

jsonDF = spark.read.json(maxValsData)
jsonDF.printSchema()

# Try specifying a nice schema and see what happens
schema = StructType([
    StructField("product", StringType()),
    StructField("price", StringType())])

jsonWithNiceSchema = spark.read.schema(schema).json(maxValsData)
print("This is with schema of strings")
jsonWithNiceSchema.show()

# Specify a Schema with INT instead of Long and watch the fireworks
columns = [
    StructField("product", StringType()),
    StructField("price", IntegerType())
]
badSchema = StructType(columns)

jsonWithBadSchema = spark.read.schema(badSchema).json(maxValsData)
print("This is with no error handling")
jsonWithBadSchema.show()

# Specify a schema with _corrupt_record and Spark puts the bad records into
# _corrupt_record by default.  The name of this field can be configured by
# overriding spark.sql.columnNameOfCorruptRecord

# NOTE NOTE NOTE NOTE NOTE There's a bug attached with this capability,
# The problem is you need to cache() the data or write it to HDFS in
# order to filter on _corrupt_record :(
# https://issues.apache.org/jira/browse/SPARK-21610

columns = [ StructField("product", StringType()), 
    StructField("price", IntegerType()),
    StructField("_corrupt_record", StringType()) 
]

handleErrorsSchema = StructType(columns)
jsonWithHandleErrorsSchema = spark.read.schema(handleErrorsSchema).json(maxValsData)
print("This is with handling errors")
jsonWithHandleErrorsSchema.show()

# Override column name of bad record
columns = [ StructField("product", StringType()), 
    StructField("price", IntegerType()),
    StructField("rufus", StringType()) 
]
handleErrorsSchema = StructType(columns)
jsonWithDifferentErrorColumn = spark.read.schema(handleErrorsSchema)\
    .json(maxValsData, columnNameOfCorruptRecord = "rufus")

print("This is with handling errors in rufus column")
# jsonWithDifferentErrorColumn.show()
# This is required :-(
# jsonWithDifferentErrorColumn.unpersist()
print("These are good records:")
onlyGood = jsonWithDifferentErrorColumn.filter("rufus IS NULL")
onlyGood.show()
print("These are bad records:")
onlyBad = jsonWithDifferentErrorColumn.filter("rufus IS NOT NULL")
onlyBad.show()

