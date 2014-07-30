#!/bin/bash
OLD_DIR=$PWD
cd $HOME
echo "Running Spark Shell from your $HOME directory.  There (hopefully is no) log4j.properties in $HOME."
echo "Notice that WARN and INFO messages will appear when you run spark-shell"
echo "Enter :quit in the Spark Shell when you come to the spark> prompt.  Press a key to run"
read FOO
spark-shell

clear
cd $OLD_DIR
echo "Now running Spark Shell with thel log4j.properties in this directory specified.  "
echo "WARN and INFO messages should NOT appear in the Spark shell."
echo "Enter :quit in the Spark Shell when you come to the spark> prompt.  Press a key to run"
read FOO
spark-shell
