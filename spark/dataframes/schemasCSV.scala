// Put the CSV files in this directory into a
// directory "csvdata" in HDFS prior to running this
val opts = Map("header" -> "true",
               "inferSchema" -> "true")
val csvDF = spark.read.options(opts).csv("csvdata")
csvDF.printSchema()
// csvDF.show()


// Try specifying a schema and see what happens
import org.apache.spark.sql.types.{StringType,StructField,StructType}
val columnsList = List(
	StructField("firstname", StringType),
	StructField("lastname", StringType)
)

val peopleSchema = StructType(columnsList)
val csvDFWithSchema = spark.read.option("header", "true").
    schema(peopleSchema).csv("csvdata")
csvDFWithSchema.show()
