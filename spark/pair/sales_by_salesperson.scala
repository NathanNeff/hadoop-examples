val inputFile = "file:/home/training/src/hadoop-examples/spark/pair/sales.txt"
val sales = sc.textFile(inputFile)
val sales_pairs =
    sales.map(sale => (sale.split('\t')(0),
                       (sale.split('\t')(1)).toInt))
val sales_by_salesperson =
    sales_pairs.reduceByKey((s1, s2) =>
       s1 + s2)
sales_by_salesperson.take(10)
