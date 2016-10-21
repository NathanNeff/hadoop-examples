sqlContext.sql("DROP TABLE IF EXISTS favorite_foods")
val sql = """
CREATE EXTERNAL TABLE favorite_foods(
userid INT,
favorite_foods STRING)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
"""
sqlContext.sql(sql)

// http://spark.apache.org/docs/1.6.0/api/scala/index.html#org.apache.spark.sql.DataFrame

val fav_foods = sqlContext.read.table("favorite_foods")

val expl = fav_foods.explode("favorite_foods", "favorite_food") {
	foods:String => foods.split(",")
}

expl.columns
expl.show()

val lateral = sqlContext.sql("SELECT userid, favorite_food FROM favorite_foods LATERAL VIEW explode(SPLIT(favorite_foods,',')) adTable AS favorite_food")

lateral.columns
lateral.show()

