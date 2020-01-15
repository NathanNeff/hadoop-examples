#!/bin/bash
# You can find the python tar gz using a command like this:
# sudo find / -follow -iname "*python*tar*gz" 2>/dev/null
PYTHON_EXAMPLES_FILE=/opt/cloudera/parcels/CDH/lib/spark/python.tar.gz
cd
mkdir -p ~/python-examples && cd ~/python-examples
tar -xzvf $PYTHON_EXAMPLES_FILE
ls -lR
echo Have fun!
