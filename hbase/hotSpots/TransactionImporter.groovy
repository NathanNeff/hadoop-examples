#!/bin/env groovy
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.HTableDescriptor
import org.apache.hadoop.hbase.HColumnDescriptor
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.client.HConnectionManager
import org.apache.hadoop.hbase.client.HConnection
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes
import groovy.time.*


def cli = new CliBuilder(usage: 'TransactionImporter.groovy [-h|--help] [-c|--create] [-n|--numRecords] [ -t|--tblName] ', 
                         parser: new org.apache.commons.cli.GnuParser ())

cli.h(longOpt:'help', 'show usage information and quit')
cli.t(longOpt:'tblName', args:1, required:false, 'Table name to use when ingesting records')
cli.n(longOpt:'numRecords', args:1, required:false, 'Number of records to import')
cli.c(longOpt:'createTable', args:0, required:false, 'Drop/create table')


/* Setup */
def opt = cli.parse(args)
if (opt.h) {
         cli.usage()
         return 0
}

def createTable = opt.c ?: false;
def numRows = (opt.n) ?: 10 * 1000 * 1000
def tableName = (opt.t) ?: 'njn_transactions'
def flushEvery = 100 * 1000

HBaseConfiguration conf = new HBaseConfiguration()
HConnection connection = HConnectionManager.createConnection(conf)

if (createTable) {
        admin = new HBaseAdmin(conf)

        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName) 
            admin.deleteTable(tableName)
        }

        def desc = new HTableDescriptor(Bytes.toBytes(tableName))
        desc.addFamily(new HColumnDescriptor(Bytes.toBytes("info")))
        admin.createTable(desc)
}

transactionsTable = connection.getTable(tableName)
transactionsTable.setAutoFlush(false)

def start = new Date()

ta = new TransactionFactory()

(0 .. numRows).each { num ->
        t1 = ta.getNewTrans()


        Put p = new Put(Bytes.toBytes(t1['key']))
        [ 'dt', 'symbol', 'price' ].each() { fld ->
                p.add(Bytes.toBytes("info"), Bytes.toBytes(fld), Bytes.toBytes(t1[fld]))
        }
        transactionsTable.put(p)

        if (num % flushEvery == 0) {
                transactionsTable.flushCommits()
                println "Flushed ${num}"
        }
}

transactionsTable.flushCommits()
TimeDuration duration = TimeCategory.minus(new Date(), start)
println "Done, ${numRows} row insert insert took " + duration
transactionsTable.close()
