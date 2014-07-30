# Log-Levels

Run ./run.sh to see the difference.  If ./log4j.properties is in the working
directory of Spark-Shell, then it will (hopefully) read and adhere to the log4j.properties
in the directory where you fire off the shell.

./log4j.properties.with.debug.log.level is an example of setting Spark's 
log level to DEBUG.

./log4j.properties is an example of setting Spark's log level to ERROR 
(to include only critical error messages in Spark's output)

$SPARK_HOME/conf

Example, if you installed Spark in your home directory,
$HOME/tools/spark-1.0.0/conf/log4j.properties
