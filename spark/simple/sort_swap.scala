val data = sc.textFile("data.txt").map(_.split("\t"))
val sales_by_salesperson = data.map(s => (s(0), s(1)))
sales_by_salesperson.collect()

