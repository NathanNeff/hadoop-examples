// Note:  Put data/people.json into the HDFS home directory
val dfPeople = spark.read.json("people.json")
val dfInterests = spark.read.json("interests.json")

dfPeople.join(dfInterests, lower($"firstName") === lower($"fname"), "left_outer").
		select("firstName", "interest").show()

dfPeople.join(dfInterests, lower($"firstName") === lower($"fname"), "right_outer").
		select("firstName", "interest").show()

dfPeople.join(dfInterests).where($"firstName" === $"fname").
		select("firstName", "interest").show()
