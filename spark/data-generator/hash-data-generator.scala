/*
curl https://raw.githubusercontent.com/eneko/data-repository/master/data/words.txt > words.txt
sudo -u hdfs hdfs dfs -mkdir /user/ec2-user
sudo -u hdfs hdfs dfs -chown ec2-user /user/ec2-user
hdfs dfs -put words.txt /user/ec2-user/words
*/
import java.security.MessageDigest

val words = sc.textFile("words", 20)
val moreWords = words.flatMap(word => List(word, word.toUpperCase(),
                                             word.toLowerCase(),
                                             word.reverse.toUpperCase(),
                                             word + "!",
                                             word + "?",
                                             word.reverse.toLowerCase()))

val md5s = moreWords.mapPartitions{iterator =>
    val md5 = MessageDigest.getInstance("MD5")
    val sha1 = MessageDigest.getInstance("SHA-1")
    val sha256 = MessageDigest.getInstance("SHA-256")
    iterator.map(word => List(word,
                         md5.digest(word.getBytes).map("%02x".format(_)).mkString,
                         sha1.digest(word.getBytes).map("%02x".format(_)).mkString,
                         sha256.digest(word.getBytes).map("%02x".format(_)).mkString).mkString("\t"))
}

md5s.saveAsTextFile("hash-data")
