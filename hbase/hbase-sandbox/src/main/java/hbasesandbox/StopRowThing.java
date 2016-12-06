package hbasesandbox;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.metrics.ScanMetrics;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class StopRowThing {
	final byte[] TBL_SRT = Bytes.toBytes("StopRowTest");
	final byte[] CF_INFO = Bytes.toBytes("info");
	
	private Configuration conf ;
	private HConnection connection;
	private UtilityThing u ;
	private HTableInterface tblConn; 
	
	public StopRowThing() throws Exception {
				
		this.conf = HBaseConfiguration.create();
		this.connection = HConnectionManager.createConnection(conf);
		this.u = new UtilityThing(connection);	

	}

	public static void main(String[] args) throws Exception {
		StopRowThing srt = new StopRowThing();

		System.out.println("Starting StopRowThing");
		srt.createTable();
		// These \uffff etc is taken directly from
		// hbase-thrift/src/test/java/org/apache/hadoop/hbase/thrift2/TestThriftHBaseServiceHandler.java
		srt.putData();
		srt.scanAllRecords();
		srt.scanRecordsWithStopRow("c");
		srt.scanRecordsWithPrefixFilter("c");
		System.out.println("Done with StopRowThing");
	}
	
	public void putData() throws Exception {
		putRecord("a", "name", "a");
		putRecord("b", "name", "b");
		putRecord("c", "name", "b");
		putRecord("c0", "name", "b0");
		putRecord("c1", "name", "b1");
		putRecord("c\u0000", "name", "b0");
		putRecord("c\u0001", "name", "b0001");
		putRecord("c\ufffe", "name", "buffe");
		putRecord("c\uffff", "name", "bufff");
		putRecord("d", "name", "c");
		
	}
	public void createTable() throws Exception {
		byte[][] splitPoints = {
				Bytes.toBytes("b"),
				Bytes.toBytes("c")
		};
		u.createTable(TBL_SRT, "info", splitPoints);
		this.tblConn = connection.getTable(TBL_SRT);

	}
	
	public void putRecord(String rowKey, String column, String value) throws Exception {
		Put p = new Put(Bytes.toBytes(rowKey));
		p.add(CF_INFO, Bytes.toBytes(column),Bytes.toBytes(value));
		tblConn.put(p);
	}
	
	private void scanAllRecords() throws Exception {
		Scan s = new Scan();
		scanAndPrintResults("Scanning all records", s);
	}
	
	private void scanRecordsWithStopRow(String stopRow) throws Exception {
		Scan s = new Scan();
		s.setStopRow(Bytes.toBytes(stopRow));
		scanAndPrintResults("Scanning records with StopRow:" + stopRow, s);
	}
	
	private void scanAndPrintResults(String message, Scan s) throws Exception {
		// The scan metrics stuff is taken directly from
		// TestFromClientSide.java in HBase 0.98 
		// https://github.com/apache/hbase/blob/c7c45f2c85cddd860a293fe9364b2b7ab0ab5bba/hbase-server/src/test/java/org/apache/hadoop/hbase/client/TestFromClientSide.java#L4918
	    System.out.println(message);
		s.setAttribute(Scan.SCAN_ATTRIBUTES_METRICS_ENABLE, Bytes.toBytes(Boolean.TRUE));
		ResultScanner rs = tblConn.getScanner(s);
		for (Result res : rs) {
			System.out.print("\t");
			u.printRow(res);
		}
		/*
		 *  This is taken directly from TestFromClientSide.java
		 */
		ScanMetrics sm = u.getScanMetrics(s);
		System.out.println("Regions Scanned: " + sm.countOfRegions.toString());
		System.out.println(StringUtils.repeat("-", 25));
	}
	
	private void scanRecordsWithPrefixFilter(String prefix) throws Exception {
		Scan s = new Scan();
		// Wish I could call Scan.setRowPrefix in this version of HBase!
		// Instead, I will Scan ALL Regions until this PrefixFilter hits
		// "b", then it will quit.
		s.setFilter(new PrefixFilter(Bytes.toBytes(prefix)));
		
		scanAndPrintResults("Scan with ONLY PrefixFilter for " + prefix, s);
	}
}
