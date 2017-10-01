// Note:  Put data/people.json into the HDFS home directory
val df = spark.read.json("people.json")

assert("org.apache.spark.sql.Column" == df("firstName").getClass.getName)

val firstNameTimesThree = df("firstName") * 3
assert("org.apache.spark.sql.Column" == firstNameTimesThree.getClass.getName)

val someColLike = $"firstName" like "Bo%"
assert("org.apache.spark.sql.Column" == someColLike.getClass.getName)

