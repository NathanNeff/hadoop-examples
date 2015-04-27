#!/usr/bin/python
# Generate data for big col fam and small col fam
# see how HBase flushes the individual colfams (together or not)
# Requires /usr/share/dict/words
# Requires a table "words" with "bigcf" and "littlecf"
# column families

from thrift.transport import TSocket
from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport
from hbase import Hbase
import os
import os.path
import random
import sys
import locale

class DataImporter():
        def __init__(self, client):
                self.client = client
                self.table_name = "numbers"
                self.max_rows = 1000 * 1000
                self.batch_size = 10 * 1000

        def batch_put(self, curr_number, row_batch):
                fc = locale.format("%d", curr_number, grouping=True)
                print "%s of %s" % (fc, locale.format("%d", self.max_rows, grouping=True))
                client.mutateRows(self.table_name, row_batch)

        
        def put_one_record(self, curr_number):
                mutations = [Hbase.Mutation(column='smallcf:some_number', value=str(curr_number))]
                client.mutateRow(self.table_name, str(curr_number), mutations)

        def import_data(self):
                # Import rows in batches, to speed it up
                row_batch = []

                for curr_number in range(1, self.max_rows):

                        # Put batch_size records
                        if curr_number % self.batch_size == 0:
                                self.batch_put(curr_number, row_batch)
                                self.put_one_record(curr_number)
                                row_batch = []

                        row = []

                        # Add this cell
                        cell_value = str(curr_number) * 100
                        cell_data = Hbase.Mutation(column="bigcf:some_number", value=cell_value)
                        row.append(cell_data)
                                
                        # Add this cell to the "row batch"
                        row_batch.append(Hbase.BatchMutation(row=str(curr_number),mutations=row))
                        
                # One final batch-put
                self.batch_put(curr_number, row_batch)
                self.put_one_record(curr_number)

        def create_table_if_not_exists(self):
                if self.table_name in self.client.getTableNames():
                        return False
                else:
                        print "Creating table '%s'" % self.table_name
                        colfams = [ Hbase.ColumnDescriptor(name="bigcf"),
                                    Hbase.ColumnDescriptor(name="smallcf") ]

                        client.createTable(self.table_name, colfams)
                        return True


if __name__ == "__main__":
        if len(sys.argv) > 1:
                thriftserver = sys.argv[1]
        else: 
                thriftserver = "localhost"

        locale.setlocale(locale.LC_ALL, 'en_US')

        # Connect to HBase Thrift server
        transport = TTransport.TBufferedTransport(TSocket.TSocket(thriftserver, 9090))
        protocol = TBinaryProtocol.TBinaryProtocolAccelerated(transport)

        # Create and open the client connection
        client = Hbase.Client(protocol)
        transport.open()

        data_importer = DataImporter(client)
        if data_importer.create_table_if_not_exists():
                data_importer.import_data()
        else:
                # Do not modify an existing table
                print "Table '%s' already exists.  Please drop it, or specify another table name." % data_importer.table_name
                
        transport.close()

