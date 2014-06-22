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

/* Setup */
def transactionsTable
def numRows = 1000 * 1000
def flushEvery = 10 * 1000
def tableName = "njn_transactions"
def shouldCreateTable = true

HBaseConfiguration conf = new HBaseConfiguration()
HConnection connection = HConnectionManager.createConnection(conf)

if (shouldCreateTable) {
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
        }
}

transactionsTable.flushCommits()
TimeDuration duration = TimeCategory.minus(new Date(), start)
println "Done, ${numRows} row insert insert took " + duration
transactionsTable.close()
