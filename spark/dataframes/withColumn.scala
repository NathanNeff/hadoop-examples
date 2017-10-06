val flightsSeq = Seq("""{ "flight_num":1,   "dt":"2017-01-01" }""",
                      """{ "flight_num":2, "dt":"2017-01-02" }""",
                      """{ "flight_num":3, "dt":"2017-01-03" }""",
                      """{ "flight_num":4, "dt":"2017-01-04" }""")

val stringRDD = sc.parallelize(flightsSeq)
val flightsDS = spark.read.json(stringRDD)

// There's a better way to do this (see below!)
val enhanced = flightsDS.withColumn(
    "DayOfWeek", date_format($"dt", "E")).withColumn(
    "isSaturday", date_format($"dt", "E") === "Sat").withColumn(
    "isSunday", date_format($"dt", "E") === "Sun").withColumn(
    "isMonday", date_format($"dt", "E") === "Mon").withColumn(
    "isTuesday", date_format($"dt", "E") === "Tue").withColumn(
    "isWednesday", date_format($"dt", "E") === "Wed").withColumn(
    "isThursday", date_format($"dt", "E") === "Thu").withColumn(
    "isFriday", date_format($"dt", "E") === "Fri")


enhanced.show()

// There's a better (e.g. functional way to do this but for now . . . .)
var enhancedBetter = flightsDS
for (day <- Array("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")) {
    enhancedBetter = 
        enhancedBetter.withColumn("is" + day,  
                            date_format($"dt", "E") === day)
}
               
