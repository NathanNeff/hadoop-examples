#!/bin/env groovy
// This will load data from movieratings flat file into a table xx_users
// The data is simply put into the key, and fake values are put into ratings column family
// it is simply used to show data ingestion using the HBase API
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
def ratingsTable
def tableName = "njn_users"
def shouldCreateTable = true
def shouldPreSplit = true

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

ratingsTable = connection.getTable(tableName)

def start = new Date()

Put p = new Put(Bytes.toBytes("StevesKey"))
p.add(Bytes.toBytes("info"), Bytes.toBytes("fname"), Bytes.toBytes("Steve"))
ratingsTable.put(p)

TimeDuration duration = TimeCategory.minus(new Date(), start)

println "Done, a one-row insert took " + duration

