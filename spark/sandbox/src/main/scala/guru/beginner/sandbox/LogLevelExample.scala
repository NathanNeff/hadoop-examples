package guru.beginner.sandbox

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object LogLevelExample {
  def main(args: Array[String]): Unit = {
    Logger.getRootLogger().setLevel(Level.OFF)
    Logger.getLogger("org.apache.spark").setLevel(Level.OFF)

    val infile = args(0)

    val conf = new SparkConf().setAppName("Sandbox Log Level").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val data = sc.textFile(infile)
    println(data.count())
  }

}
