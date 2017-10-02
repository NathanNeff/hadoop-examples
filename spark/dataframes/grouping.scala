// Note:  Put data/people.json into the HDFS home directory
val df = spark.read.json("people.json")

val grpd = df.groupBy("testScore").agg(min($"firstName") as "First FirstName",
                                       max($"firstName") as "Last FirstName",
                                       min($"studyTime") as "min studytime",
                                       sum($"studyTime") as "all_studytime").
                                   where($"all_studytime" > 100)
grpd.show()

// Grouping with LEFT OUTER JOIN, and showing zero for
// count of rows with no corresponding child elements

val customerSeq = Seq("""{ "firstName":"Nate",   "id":1 }""",
                      """{ "firstName":"Jackie", "id":2 }""")
val customersDS = spark.createDataset(customerSeq)
val customersDF = spark.read.json(customersDS)

val ordersSeq = Seq("""{ "cust_id":1, "product":"Something", "order_id":2}""")
val ordersDS = spark.createDataset(ordersSeq)
val ordersDF = spark.read.json(ordersDS)

val customerOrders = customersDF.join(ordersDF, $"id" === $"cust_id", "left_outer")

val customerOrderCounts = customerOrders.
    groupBy($"firstName", $"id").
    agg(count($"cust_id") as "num_orders")

customerOrderCounts.show()

