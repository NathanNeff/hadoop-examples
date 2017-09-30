// Put the CSV files in this directory into a
// directory "csvdata" in HDFS prior to running this
val opts = Map("header" -> "true",
               "inferSchema" -> "true")
val csvDF = spark.read.options(opts).csv("csvdata")
csvDF.printSchema()
csvDF.show()
