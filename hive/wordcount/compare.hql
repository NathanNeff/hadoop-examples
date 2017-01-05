SET hive.cli.print.header=true;

SELECT wordcount.word, wordcount.count AS hive_count, mr_wordcount.count AS mr_count
FROM wordcount
FULL OUTER JOIN mr_wordcount on (mr_wordcount.word = wordcount.word)
WHERE wordcount.word IS NULL or
mr_wordcount.word IS NULL;
