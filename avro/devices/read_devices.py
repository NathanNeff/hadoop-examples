import avro.schema
from avro.datafile import DataFileReader, DataFileWriter
from avro.io import DatumReader, DatumWriter

reader = DataFileReader(open("devices.avro", "r"), DatumReader())
for device in reader:
    print device
reader.close()
