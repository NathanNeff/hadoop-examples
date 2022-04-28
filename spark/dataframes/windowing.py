# Spark SQL syntax for windowing
# https://spark.apache.org/docs/latest/sql-ref-syntax-qry-select-window.html
df = spark.range(10)
df.createOrReplaceTempView("df")
spark.sql("""
SELECT id,
  COUNT(id) OVER (ORDER BY id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) cum_count,
  SUM(id) OVER (ORDER BY id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) cum_count
  FROM df
""").show()

Example of LAG
spark.sql("""
SELECT rider_id, date_time,
  LAG(date_time) OVER (PARTITION BY rider_id ORDER BY date_time) date_time_previous
  FROM rides
""").show()
