// Note:  Put data/people.json into the HDFS home directory
val df = spark.read.json("people.json")

val grpd = df.groupBy("testScore").agg(min($"firstName") as "First FirstName",
                                       max($"firstName") as "Last FirstName",
                                       min($"studyTime") as "min studytime",
                                       sum($"studyTime") as "all_studytime").
                                   where($"all_studytime" > 100)
grpd.show()
