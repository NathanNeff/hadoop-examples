// Note:  Put data/people.json into the HDFS home directory
val dfPeople = spark.read.json("people.json")
val dfInterests = spark.read.json("interests.json")

val dfPeopleAndInterests = dfPeople.join(dfInterests, lower($"firstName") === lower($"fname"), "left_outer")
dfPeopleAndInterests.select("firstName", "interest").show()
