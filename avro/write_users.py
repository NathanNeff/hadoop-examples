#!/usr/bin/env python
import avro.schema
from avro.datafile import DataFileReader, DataFileWriter
from avro.io import DatumReader, DatumWriter

schema = avro.schema.parse(open("user.avsc").read())

writer = DataFileWriter(open("users.avro", "w"), DatumWriter(), schema)
writer.append({"fullname": "Alyssa", "favorite_number": 256})
writer.append({"fullname": "Ben", "favorite_number": 7, "favorite_color": "red"})
writer.close()
