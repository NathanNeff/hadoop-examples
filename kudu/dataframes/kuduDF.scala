val customersDF = spark.read.format("org.apache.kudu.spark.kudu").
    option("kudu.master", "master-2:7051").
    option("kudu.table", "customers").
    load()

customersDF.show(10)

// Reverse the name
val customersReversedNameDF = customersDF.withColumn("name", reverse(customersDF("name")))

customersReversedNameDF.write.format("org.apache.kudu.spark.kudu").
    option("kudu.master", "master-2:7051").
    option("kudu.table", "customers").
    mode("append").
    save()

// <mackey>Requery the data, m'kay?</mackey>
val customersDFAfterReverse = spark.read.format("org.apache.kudu.spark.kudu").
    option("kudu.master", "master-2:7051").
    option("kudu.table", "customers").
    load()
customersDFAfterReverse.show(10)
