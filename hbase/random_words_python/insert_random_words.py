#!/usr/bin/python

from thrift.transport import TSocket
from thrift.protocol import TBinaryProtocol
from thrift.transport import TTransport
from hbase import Hbase
import os
import os.path
import random
import sys

if len(sys.argv) > 1:
        thriftserver = sys.argv[1]
else: 
        thriftserver = "localhost"

random.seed()
words = [line.strip() for line in open('/usr/share/dict/words')]
max = len(words) - 1

# Connect to HBase Thrift server
# Assumes that thrift server is localhost
transport = TTransport.TBufferedTransport(TSocket.TSocket(thriftserver, 9090))
protocol = TBinaryProtocol.TBinaryProtocolAccelerated(transport)

# Create and open the client connection
client = Hbase.Client(protocol)
transport.open()

# Create a list of mutations per batch
mutationsbatch = []

# Create 1 billion data rows
num_rows = 1000000
batchsize = 10000

mutationsbatch = []
for x in range(0, num_rows - 1):
        if x % batchsize == 0:
                print "Pushing " + str(x)
                client.mutateRows("words", mutationsbatch)
                mutationsbatch = []
                r = random.randint(0, max)
	
        row = []

	# Add this cell
	row.append(Hbase.Mutation(column="w:" + words[r] + "@" + str(x), value=str(1)))
		
        thisword = words[r]
	mutationsbatch.append(Hbase.BatchMutation(row=thisword + str(r),mutations=row))
	
# Run the mutations for the words
client.mutateRows("words", mutationsbatch)
	
transport.close()
