# HBase Sandbox

## Stop Row Thing

Java class that creates a table, splits it into X number of regions, then performs various Scans.
Scans use no start/stop row, stop row and PrefixFilter to demonstrate the # of regions that are
scanned and rows returned using various combinations of start/stop row, and Prefix filters.

To run, use:

	./runStopRowThing.sh

You can use Eclipse to work with the code.  To create an Eclipse project, use:

	mvn eclipse:eclipse

It should then be possible to use Eclipse to modify / run the Java source code.

Log level can be tuned by editing src/main/resources/log4j.properties and
setting hbase.root.logger=INFO,console
