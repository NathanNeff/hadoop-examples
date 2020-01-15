def printDataFrame(df, name):
   print("This is %s" % name)
   df.show()

leftFile = "hadoop-examples-data/left.csv"
rightFile = "hadoop-examples-data/right.csv"
opts = {
    "inferSchema" : True,
    "header"      : True }

leftDF = spark.read.options(**opts).csv(leftFile)
rightDF = spark.read.options(**opts).csv(rightFile)
antiJoinLeftRightDF = leftDF.join(rightDF, "name", "left_anti")
antiJoinRightLeftDF = rightDF.join(leftDF, "name", "left_anti")

printDataFrame(leftDF, "Left")
printDataFrame(rightDF, "Right")
printDataFrame(antiJoinLeftRightDF, "Left Anti-Joined to Right")
printDataFrame(antiJoinRightLeftDF, "Right Anti-Joined to Left")

# Inner Join
innerJoinDF = leftDF.join(rightDF, "name")
printDataFrame(innerJoinDF, "Inner Join")

# Ambiguous Columns
# This is the problem:  There's ambiguous column "region"
print("This is innerJoinDF:")
innerJoinDF.printSchema()
# What does this look like in Spark SQL?
innerJoinDFView = innerJoinDF.createOrReplaceTempView("inner_join_df")
print("This is DESCRIBE:")
spark.sql("DESCRIBE inner_join_df").show()

# There's many ways to create column names which are
# disambiguous.
# - https://sparkbyexamples.com/spark/rename-a-column-on-spark-dataframes/
# - https://stackoverflow.com/questions/33778664/spark-dataframe-distinguish-columns-with-duplicated-name#33779190
# I guess it depends on where you wish to peform the renaming

# One way would be to work with temp views and SparkSQL from the get-go
leftDF.createOrReplaceTempView("leftdf")
rightDF.createOrReplaceTempView("rightDF")
sql = """
SELECT * FROM leftdf
JOIN rightdf ON leftdf.name = rightdf.name
"""

# However, the resulting dataframe doesn't have the nice "leftdf.name" columns :(
spark.sql(sql).createOrReplaceTempView("huh")
spark.sql("DESCRIBE huh").show()
