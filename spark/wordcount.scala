val textFile = sc.textFile("somedata.txt")
textFile.count() // Number of items in this RDD
textFile.first() // First item in this RDD
val linesWithSpark = textFile.filter(line => line.contains("Spark"))
textFile.filter(line => line.contains("Spark")).count() // How many lines contain "Spark"?
println linesWithSpark
exit
