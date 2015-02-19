#!/usr/bin/env python
import avro.schema
from avro.datafile import DataFileReader
from avro.io import DatumReader

reader = DataFileReader(open("users.avro", "r"), DatumReader())
for user in reader:
        print user
reader.close()
