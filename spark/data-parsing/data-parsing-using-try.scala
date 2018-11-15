import scala.util.{Try, Failure, Success}
// Define test data
// With thanks to http://rcardin.github.io/big-data/apache-spark/scala/programming/2016/09/25/try-again-apache-spark.html
val orig_data = Array(
	"1",
	"2",
    "trash",
    "4"
)

val data = sc.parallelize(orig_data)
val weblogs = data.map(line => Try(line.toInt))
println(weblogs.getClass)
val good = weblogs.filter(d => d.isSuccess)
weblogs.collect()
good.collect()
