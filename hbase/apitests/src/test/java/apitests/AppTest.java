package apitests;

import java.io.IOException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	public void notestApp() throws IOException {

		Configuration configuration = HBaseConfiguration.create();
		assertEquals("hdfs://localhost:8020/hbase", configuration.get("hbase.rootdir"));
		assertNull(configuration.get("dfs.datanode.data.dir"));

		HBaseAdmin admin = new HBaseAdmin(configuration);
		HTableDescriptor tblDesc = new HTableDescriptor(TableName.valueOf("bork1"));

		HColumnDescriptor colFam = new HColumnDescriptor(Bytes.toBytes("cf1"));
		tblDesc.addFamily(colFam);
		admin.createTable(tblDesc);
		admin.close();
		assertTrue(true);

	}

	public void testAnother() throws IOException {
		Configuration config = HBaseConfiguration.create();

		HConnection conn = HConnectionManager.createConnection(config);
		HTableInterface table = conn.getTable(TableName.valueOf("movie"));
		assertEquals("movie", table.getName().toString());
		assertTrue(true);

	}

	public void testAgain() throws IOException {

		Configuration cfg = HBaseConfiguration.create();
		HConnection conn = HConnectionManager.createConnection(cfg);
		HTableInterface table = conn.getTable(TableName.valueOf("movie"));
		Get g = new Get(Bytes.toBytes("1"));
		Result r = table.get(g);
		String rowKey = Bytes.toString(r.getRow());
		byte[] byteArray = r.getValue(Bytes.toBytes("desc"), Bytes.toBytes("title"));
		String columnValue = Bytes.toString(byteArray);
		
		assertTrue(true);
		table.close();
		conn.close();

	}
	
	public void testScan() throws IOException {
		Configuration cfg = HBaseConfiguration.create();
		HConnection conn = HConnectionManager.createConnection(cfg);
		HTableInterface table = conn.getTable(TableName.valueOf("movie"));

		
		Scan s = new Scan();
		
		byte[] startRow = Bytes.toBytes("1");
		byte[] stopRow = Bytes.toBytes("10" + 0x00);
		
		s.addFamily(Bytes.toBytes("info"));
		s.setStartRow(startRow);
		s.setStopRow(stopRow);
		ResultScanner rs = table.getScanner(s);
		for (Result r : rs) {
			
			System.out.println(Bytes.toString(r.getRow()));
		}
		
	}
	
	public void testFoo() throws IOException {
	
		Configuration cfg = HBaseConfiguration.create();
		HConnection conn = HConnectionManager.createConnection(cfg);
		HTableInterface table =
		conn.getTable(TableName.valueOf("movie"));
		Put p = new Put(Bytes.toBytes("rowkey1"));
		p.add(Bytes.toBytes("info"), Bytes.toBytes("moviename"), Bytes.toBytes("E.T."));
		table.put(p);
		table.close();
		conn.close();
	
	
	}
	
}
