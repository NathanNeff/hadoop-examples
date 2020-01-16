#!/bin/bash
# You can find the python tar gz using a command like this:
# sudo find / -follow -iname "*python*tar*gz" 2>/dev/null
PYTHON_EXAMPLES_FILE=/opt/cloudera/parcels/CDH/lib/spark/python.tar.gz
cd
mkdir -p ~/python-examples && cd ~/python-examples
tar -xzvf $PYTHON_EXAMPLES_FILE
ls -lR

# Example of running code (Remove the # in front of spark-submit)
# spark-submit als.py
# 2>/dev/null is a quick and dirty add which silences noisy log messages
# spark-submit pi.py 100 2>/dev/null
