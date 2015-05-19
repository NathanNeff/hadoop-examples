val inputFile = "file:/home/training/src/hadoop-examples/spark/pair/weblogs.txt"
val weblogs = sc.textFile(inputFile)
val ips_and_page = weblogs.map(s => (s.split(' ')(0) + '::' + s.split(' ')(2)))
ips_and_page.take(1)
