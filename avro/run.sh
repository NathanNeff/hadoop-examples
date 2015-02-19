#!/bin/bash
# Find your own eggs on your system
EGG_DIR=/opt/cloudera/parcels/CDH-5.1.2-1.cdh5.1.2.p0.3/lib/hue/build/env/lib/python2.6/site-packages/avro-1.7.6-py2.6.egg
export PYTHONPATH=$EGG_DIR
python ./write_bunch_of_users.py
python ./read_users.py
