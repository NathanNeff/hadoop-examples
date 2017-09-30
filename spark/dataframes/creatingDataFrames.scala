import sys.process._

if(Seq("hdfs", "dfs", "-test", "-d", "people").! == 0) {
		Seq("hdfs", "dfs", "-rm", "-R", "people").!
}
val someDFReader = spark.read
assert("org.apache.spark.sql.DataFrameReader" == someDFReader.getClass.getName)

val first =  Seq(Tuple1("Arvin"), Tuple1("Betty"))
val second =  Seq(Tuple1("Chris"), Tuple1("Derek"))
val third =  Seq(Tuple1("Eric"), Tuple1("Ferris"))

spark.createDataFrame(first).write.save("people")

spark.createDataFrame(second).
		write.mode("append").save("people")

spark.createDataFrame(third).
		write.mode("ignore").save("people")

val allPeople = spark.read.load("people")
allPeople.count()
"hdfs dfs -ls people".!



