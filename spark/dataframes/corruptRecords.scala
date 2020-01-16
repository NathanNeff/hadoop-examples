import org.apache.spark.sql.types._

// Put the json files in data subdirectory into HDFS prior to running this
// hdfs dfs -put hadoop-examples-data
val maxValsData = "hadoop-examples-data/maxVals.json"

// Run this code using spark-shell using :load <this file>
// (recommend using `:silent` prior to calling
// to silence the output of defining the functions)

// Comment / uncomment the following function calls to see 
// how spark's dataframes work w/schemas
useNoSchema()

useStringSchema()

useIntegerSchemaNoCorruptRecord()

// Cause crash due to 
//   https://issues.apache.org/jira/browse/SPARK-29621
//   and https://issues.apache.org/jira/browse/SPARK-21610
useIntegerSchemaAndCorruptRecord(showCount = true)

// Avoid crash
useIntegerSchemaAndCorruptRecord(showCount = false)

// Use different error column name
useRufus()
useSchemaWithLongValue()

println("Done")
def useNoSchema() {
    val jsonDF = spark.read.json(maxValsData)
    println("This is with no schema:")
    jsonDF.printSchema()
}

def useStringSchema() {
    val columnsList = List(
	    StructField("product", StringType),
	    StructField("price", StringType)
    )
    val jsonWithSchema = spark.read.schema(StructType(columnsList)).json(maxValsData)
    println("This is with String Schema")
    jsonWithSchema.show()
}


// ------ Try specifying a schema with string and INT
// ------ And with no _corrupt_record field
def useIntegerSchemaNoCorruptRecord() {
    val columnsList = List(
        StructField("product", StringType),
        StructField("price", IntegerType)
    )
    val jsonWithBadSchema = spark.read.schema(StructType(columnsList)).json(maxValsData)
    println("This is with String and INT Schema, but no _corrupt_record field")
    jsonWithBadSchema.show()
}


// ------ Specify schema with _corrupt_record and try to count()
// Using .count() with _corrupt_record will crash:
//   See: https://issues.apache.org/jira/browse/SPARK-29621
//   and https://issues.apache.org/jira/browse/SPARK-21610
// You can change the name of _corrupt_record field, see "useRufus" below
def useIntegerSchemaAndCorruptRecord(showCount: Boolean = false) {
    val columnsList = List(
        StructField("product", StringType),
        StructField("price", IntegerType),
        StructField("_corrupt_record", StringType)
    )

    val jsonWithBadSchemaAndCorruptRecordField = spark.read.schema(StructType(columnsList)).json(maxValsData)
    val errors = jsonWithBadSchemaAndCorruptRecordField.filter("_corrupt_record IS NULL")
    if (showCount) {
        println("This is using _corrupt_record, but count doesn't work:")
        errors.count()
    } else {
        println("This is using _corrupt_record show *DOES* work :-O")
        errors.show()
   }
}

// Change 
// Make sure to change _corrupt_record field's name in schema to "rufus"
def useRufus() {
    val columnsList = List(
        StructField("product", StringType),
        StructField("price", IntegerType),
        StructField("rufus", StringType)
    )

    val jsonWithDifferentErrorColumn = spark.read.schema(StructType(columnsList))
        .option("columnNameOfCorruptRecord", "rufus")
        .json(maxValsData)
    println("This is with error column = \"rufus\"")
    jsonWithDifferentErrorColumn.show()
}

def useSchemaWithLongValue() {
    // ----- Try using a schema with Long and it works :-)
    val columnsList = List(
        StructField("product", StringType),
        StructField("price", LongType),
        StructField("_corrupt_record", StringType)
    )
    val jsonWithLongSchema = spark.read.schema(StructType(columnsList)).json(maxValsData)
    println("This is json with LONG in Schema")
    jsonWithLongSchema.show()
}
