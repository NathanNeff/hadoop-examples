package apitests;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;


public class CreateTable {

	public static final String TEST_TABLE = "test_movie";
	public static final byte[] TEST_TABLE_BYTES = TEST_TABLE.getBytes();
	public static final String DESC_COLFAM = "desc";
	public static final byte[] DESC_COLFAM_BYTES = DESC_COLFAM.getBytes();

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
                }
                else if (connectionMethod == 1) {
                        // This works on CDH 5.3.1
                        admin = new HBaseAdmin(HBaseConfiguration.create());
                }
                else {
                        // This works on CDH 5.3.1
                        HConnection connection = 
                                HConnectionManager.createConnection(HBaseConfiguration.create());
                        admin = new HBaseAdmin(connection);
                }

		if (admin.tableExists(TEST_TABLE_BYTES)) {
                        admin.disableTable(TEST_TABLE_BYTES);
                        admin.deleteTable(TEST_TABLE_BYTES);
		}

		// Create the new table
		HTableDescriptor tableDescriptor = new HTableDescriptor();
		tableDescriptor.setName(TEST_TABLE_BYTES);

		HColumnDescriptor columnDescriptor = new HColumnDescriptor(DESC_COLFAM_BYTES);
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
