#!/bin/env groovy
// This will load data from movieratings flat file into a table xx_users
// The data is simply put into the key, and fake values are put into ratings column family
// it is simply used to show data ingestion using the HBase API
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.HTableDescriptor
import org.apache.hadoop.hbase.HColumnDescriptor
import org.apache.hadoop.hbase.client.HBaseAdmin
import org.apache.hadoop.hbase.client.HTable
import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.util.Bytes
import groovy.time.*

def createTable(String tbl, boolean shouldPreSplit) {
        if (admin.tableExists(tbl)) {
            admin.disableTable(tbl) 
            admin.deleteTable(tbl)
        }

        def desc = new HTableDescriptor(Bytes.toBytes(tbl))
        desc.addFamily(new HColumnDescriptor(Bytes.toBytes("ratings")))
        desc.addFamily(new HColumnDescriptor(Bytes.toBytes("smallcf")))

        
        if (shouldPreSplit) {
                // The keys in the input file go from 1 .. 71000
                byte[][] splits = new byte[8][]

                [ 500, 10000, 20000, 30000, 40000, 50000, 60000, 70000].eachWithIndex { splt, i ->
                            splits[i] = Bytes.toBytes(new Integer(splt))

                } 
                admin.createTable(desc, splits)
        }
        else {
                admin.createTable(desc)
        }
        return new HTable(tbl)
}

def putFromFile(ratingsTable) {
        ratingsFile = new File("/home/shared/movielens/ml-10M100K/ratings.dat")
        ratingsFile.eachLine { line, line_num ->
                Put p = new Put(Bytes.toBytes(line))
                p.add(Bytes.toBytes("ratings"), Bytes.toBytes("col1"), Bytes.toBytes(""));
                ratingsTable.put(p);
                if (line_num.mod(40000) == 0) {

                        // Put a line into the small column family
                        // to see if flushes/compactions occur region-wide or column-family wide
                        // Hint: it's region-wide
                        Put p2 = new Put(Bytes.toBytes(line.reverse()))
                                p2.add(Bytes.toBytes("smallcf"), Bytes.toBytes("col1"), Bytes.toBytes("hello, I'm small"));
                        ratingsTable.put(p2);

                        println line_num
                        ratingsTable.flushCommits()
                }
        }
}

// Returns an array with Map with userid, movieid, timestamp and rating
def genRandomMovieRatings(int numRatings, boolean sequential) {
        def ratings = []
        def seed = new Random()
        (0 .. numRatings).each {
                ratings << [ movieid:seed.nextInt(65132) + 1, userid:seed.nextInt(72000), rating:seed.nextInt(5) + 1, timestamp:new Date()]
        }
        return ratings
}

def putFromRandom(ratingsTable) {
        def start = new Date()
        def randomPuts = genRandomMovieRatings(1000 * 1000, false)
        TimeDuration duration = TimeCategory.minus(new Date(), start)
        println "it took " + duration
        randomPuts.eachWithIndex { it, i ->
                Put p = new Put(Bytes.toBytes(it.userid))
                p.add(Bytes.toBytes("ratings"), Bytes.toBytes(it.movieid), Bytes.toBytes(it.rating));
                ratingsTable.put(p);
                if (i.mod(20000) == 0) {
                        println i
                        ratingsTable.flushCommits()
                }
        }
}

/* Setup */
def ratingsTable
def shouldCreateTable = true
def shouldPreSplit = true

if (shouldCreateTable) {
        conf = HBaseConfiguration.create()
        admin = new HBaseAdmin(conf)
        ratingsTable = createTable("xx_users", shouldPreSplit)
}
else {
        ratingsTable = new HTable("xx_users")
}

def start = new Date()

ratingsTable.setAutoFlush(false)
// putFromFile(ratingsTable)
10.times {
        putFromRandom(ratingsTable)
}
ratingsTable.close()

TimeDuration duration = TimeCategory.minus(new Date(), start)

println "Done.  Import took: " + duration

