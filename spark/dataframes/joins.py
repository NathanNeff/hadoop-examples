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
