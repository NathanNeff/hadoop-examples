#!/bin/bash
AVRO_TARBALL=avro-1.7.4.tar.gz
MD5FILE=avro-1.7.4.tar.gz.md5
MIRROR=http://www.eng.lsu.edu/mirrors/apache/avro/avro-1.7.4/py

test -f $MD5FILE || wget http://www.us.apache.org/dist/avro/stable/py/$MD5FILE
test -f $AVRO_TARBALL || wget $MIRROR/$AVRO_TARBALL
md5sum -c $MD5FILE
