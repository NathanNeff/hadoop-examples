package apitests;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.util.Bytes;

public class CreateTable {

	public static final TableName TEST_TABLE = TableName
			.valueOf("test_create_table");
	public static final byte[] CF_INFO = Bytes.toBytes("info");

	/**
	 * Creates the table in HBase based on the row key type and splits
	 * 
	 * @throws Exception
	 */
	public void createTable(int connectionMethod) throws Exception {
		HBaseAdmin admin;
		if (connectionMethod == 0) {
			// This is the way that doesn't work (I don't know why yet)
			admin = new HBaseAdmin(new Configuration());
		} else if (connectionMethod == 1) {
			// This works on CDH 5.3.1
			admin = new HBaseAdmin(HBaseConfiguration.create());
		} else {
			// This works on CDH 5.3.1
			HConnection connection = HConnectionManager
					.createConnection(HBaseConfiguration.create());
			admin = new HBaseAdmin(connection);
		}

		if (admin.tableExists(TEST_TABLE)) {
			admin.disableTable(TEST_TABLE);
			admin.deleteTable(TEST_TABLE);
		}

		// Create the new table
		HTableDescriptor tableDescriptor = new HTableDescriptor();
		tableDescriptor.setName(TEST_TABLE);

		HColumnDescriptor columnDescriptor = new HColumnDescriptor(CF_INFO);
		tableDescriptor.addFamily(columnDescriptor);

		admin.createTable(tableDescriptor);
		admin.close();
	}

	public static void main(String[] args) {
		int connectionMethod = 2;

		try {
			CreateTable ct = new CreateTable();
			ct.createTable(connectionMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
