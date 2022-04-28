# Experimenting with Spark's handling of duplicate column names
# when joining dataframes.

# Run this program in an existing PySpark shell
# Reference
# - Example in column_rename_after_joins.py
# - https://sparkbyexamples.com/spark/rename-a-column-on-spark-dataframes/
# - https://stackoverflow.com/questions/50287558/how-to-rename-duplicated-columns-after-join
# - https://stackoverflow.com/questions/33778664/spark-dataframe-distinguish-columns-with-duplicated-name#33779190
#   - References this KB article:  https://kb.databricks.com/data/join-two-dataframes-duplicated-columns.html


from pyspark.sql import *
from pyspark.sql.types import *
from pyspark.sql.functions import col

def printDataFrame(df, name):
  spark.sparkContext.setJobGroup("Column Rename", name)
  print("This is %s" % name)
  df.show()

schema = StructType([ 
   StructField("name", StringType(), True), 
   StructField("region", StringType(), True)])

west = [Row("Nate", "west"),
        Row("Bob", "west"),
        Row("Sir Scratch", "west"),
        Row("Bartholomew", "west")]

east = [Row("Nate", "east"),
        Row("Bob", "east"),
        Row("Sir Scratch", "east")]

spark.sparkContext.setJobGroup("Column Rename", "Instantiate Dataframes")
westDF = spark.createDataFrame(west, schema)
eastDF = spark.createDataFrame(east, schema)

# printDataFrame(westDF, "Left")
# printDataFrame(eastDF, "Right")

# Inner Join
innerJoinDF = westDF.join(eastDF, on="name")
# printDataFrame(innerJoinDF, "Inner Join.  Notice the ambiguous column names")

# Outer Join
outerJoinDF = westDF.join(eastDF, on="name", how="outer")
# printDataFrame(outerJoinDF, "Outer Join.  Notice the ambiguous column names")

# # One way would be to work with temp views and SparkSQL from the get-go
westDF.createOrReplaceTempView("westDF")
eastDF.createOrReplaceTempView("eastDF")

sql = """
SELECT * FROM westdf
LEFT OUTER JOIN eastdf ON westdf.name = eastdf.name
"""

# However, the resulting dataframe doesn't have the nice "leftdf.name" columns :(
joined = spark.sql(sql)
printDataFrame(joined, "Joined dataframe using Spark SQL\nIt still has ambiguous columns in the Show()")

print("")
renamedColumns = joined.select(col("eastDF.name").alias("eastDF.name"),
                               col("westDF.name").alias("westDF.name"), 
                               col("eastDF.region").alias("eastDF.region"), 
                               col("westDF.region").alias("westDF.region"))

printDataFrame(renamedColumns, "renamedColumns.  We can reference individual fields using the eastdf.region and westdf.region syntax")
