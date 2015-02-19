#!/usr/bin/env python
import avro.schema
from avro.datafile import DataFileReader, DataFileWriter
from avro.io import DatumReader, DatumWriter

schema = avro.schema.parse(open("user.avsc").read())
writer = DataFileWriter(open("users.avro", "w"), DatumWriter(), schema)

dictionary_file = open('/usr/share/dict/words', 'r')

for word in dictionary_file:
        print "Adding " + word
        writer.append({"fullname": word, "favorite_number": len(word)})
        if word > "l":
                break
writer.close()
