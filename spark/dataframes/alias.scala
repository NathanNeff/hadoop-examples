// Note:  Put data/people.json into the HDFS home directory
val df = spark.read.json("people.json")

df.select($"firstName" as "fn_as", 
          $"firstName" alias "fname_alias",
          $"firstName" name "First_name").show()

