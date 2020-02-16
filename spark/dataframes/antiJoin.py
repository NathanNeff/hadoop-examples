leftFile = "hadoop-examples-data/left.csv"
rightFile = "hadoop-examples-data/right.csv"
opts = {
    "inferSchema" : True,
    "header"      : True }

leftDF = spark.read.options(**opts).csv(leftFile)
print("This is leftDF:")
leftDF.show()

rightDF = spark.read.options(**opts).csv(rightFile)
print("This is rightDF:")
rightDF.show()

antiJoinLeftRightDF = leftDF.join(rightDF, "name", "left_anti")
print("This is left anti join right")
antiJoinLeftRightDF.show()

antiJoinRightLeftDF = rightDF.join(leftDF, "name", "left_anti")
print("This is right anti join left")
antiJoinRightLeftDF.show()
