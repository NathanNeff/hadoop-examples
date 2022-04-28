# Example of using Python UDFs in Spark SQL
def my_uppercase(x):
    upper(x)
    
my_uppercase_udf = udf(my_uppercase, returnType=IntegerType())
rides_clean.createOrReplaceTempView("rides_clean")
spark.udf.register("my_uppercase_udf", my_uppercase_udf)
spark.sql("select date_time, my_uppercase_udf(date_time) from rides_clean").show()
