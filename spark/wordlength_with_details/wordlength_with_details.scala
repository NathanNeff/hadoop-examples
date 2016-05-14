val input_dir = System.getProperty("user.dir")

val words = sc.textFile("file:" + input_dir + "/data.txt").
    flatMap(line => line.split(" "))

/* ("o", "other"),
 * ("o", "otto"),
 * ("t", "tomcat"),
 * ("o", "otto"),
 */
val first_letter_and_word = words.map(
    word => (word.substring(0,1), word))

val first_letter_counts = 
	first_letter_and_word.map{ 
		case (first_letter, word) => (first_letter, 1)}.
        reduceByKey((x,y) => (x + y))


/* Produce:
 * ("o", "otto,other")
 * ("t", "too",tomcat")
 */
val first_letter_and_wordlist = 
    first_letter_and_word.distinct().
    groupByKey().
    mapValues(_.mkString(","))

val counts_with_words =
	first_letter_counts.join(first_letter_and_wordlist)

val output_dir = "file:" + input_dir + "/wordlength_with_details"
counts_with_words.saveAsTextFile(output_dir)


