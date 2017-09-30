val tableName = "testpeople"

val first =  Seq(Tuple1("Arvin"), Tuple1("Betty"))
val second =  Seq(Tuple1("Chris"), Tuple1("Derek"))
val third =  Seq(Tuple1("Eric"), Tuple1("Ferris"))

println("Writing first")
spark.createDataFrame(first).write.mode("overwrite").saveAsTable(tableName)

println("Writing second")
spark.createDataFrame(second).
		write.mode("append").saveAsTable(tableName)

println("Writing third")
spark.createDataFrame(third).write.
		option("path", "peopleinmyhomedir").
		saveAsTable(tableName)




