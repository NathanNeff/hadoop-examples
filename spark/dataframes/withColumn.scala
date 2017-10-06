val flightsSeq = Seq("""{ "flight_num":1,   "dt":"2017-01-01" }""",
                      """{ "flight_num":2, "dt":"2017-01-02" }""")
val stringRDD = sc.parallelize(flightsSeq)
val flightsDS = spark.read.json(stringRDD)

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
