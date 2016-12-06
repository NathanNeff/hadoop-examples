package hbasesandbox;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.metrics.ScanMetrics;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.util.Bytes;

public class UtilityThing {
	private HConnection connection;

	public UtilityThing(HConnection connection) {
		this.connection = connection;
	}

	public void createTable(byte[] tableNameBytes, String columnFamily, byte[][] splitBytes) throws Exception {
		this.createTable(Bytes.toString(tableNameBytes), columnFamily, splitBytes);
	}

	/**
	 * Creates the table in HBase based on the row key type and splits
	 * 
	 * @throws Exception
	 */
	public void createTable(String tableName, String columnFamily, byte[][] splitBytes) throws Exception {
		HBaseAdmin admin = new HBaseAdmin(connection);

		if (admin.tableExists(tableName)) {
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
		}

		// Create the new table
		HTableDescriptor tableDescriptor = new HTableDescriptor();
		tableDescriptor.setName(Bytes.toBytes(tableName));

		HColumnDescriptor columnDescriptor = new HColumnDescriptor(columnFamily);
		tableDescriptor.addFamily(columnDescriptor);

		if (null == splitBytes) {
			admin.createTable(tableDescriptor);
		} else {
			admin.createTable(tableDescriptor, splitBytes);
		}

		admin.close();
	}

	public ScanMetrics getScanMetrics(Scan scan) throws Exception {
		byte[] serializedMetrics = scan.getAttribute(Scan.SCAN_ATTRIBUTES_METRICS_DATA);

		ScanMetrics scanMetrics = ProtobufUtil.toScanMetrics(serializedMetrics);

		return scanMetrics;
	}
	
	public void printRow(Result r) {
		System.out.println(Bytes.toString(r.getRow()));
	}
}
