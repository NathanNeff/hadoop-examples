// Put the json files in data subdirectory into HDFS prior to running this
val jsonDF = spark.read.options(opts).json("maxVals.json")
jsonDF.printSchema()
// csvDF.show()


// Try specifying a schema and see what happens
import org.apache.spark.sql.types.{LongType, IntegerType,StringType,StructField,StructType}
val columnsList = List(
	StructField("product", StringType),
	StructField("price", StringType)
)

val jsonWithSchema = spark.read.schema(StructType(columnsList)).json("maxVals.json")
jsonWithSchema.show()

val columnsList = List(
	StructField("product", StringType),
	StructField("price", IntegerType),
    StructField("_corrupt_record", StringType)
)

val jsonWithSchema = spark.read.schema(StructType(columnsList)).json("maxVals.json")
jsonWithSchema.show()

val columnsList = List(
	StructField("product", StringType),
	StructField("price", LongType)
)

val jsonWithLongSchema = spark.read.schema(StructType(columnsList)).json("maxVals.json")
jsonWithLongSchema.show()
