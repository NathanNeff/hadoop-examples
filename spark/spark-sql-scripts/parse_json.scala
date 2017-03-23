import sqlContext.implicits._
import org.apache.spark.sql._

val curDir = System.getProperty("user.dir")
val json = sqlContext.read.json("file:" + curDir + "/data/people.json")
json.printSchema()
json.registerTempTable("json")

/* 
   TODO figure out what the table_name versus column_name
   means when EXPLODING arrays of structs
   https://docs.databricks.com/spark/latest/spark-sql/language-manual/select.html
   (Remove OUTER to only get people with relatives)
*/ 
val flattened_table = sqlContext.sql("""SELECT name, r.name AS relative_name, r.rel as foo 
										FROM json 
										LATERAL VIEW OUTER EXPLODE(relatives) r AS r""")

flattened_table.show()
flattened_table.printSchema


