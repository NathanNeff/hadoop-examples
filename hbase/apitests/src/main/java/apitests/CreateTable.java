package apitests;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

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
	public void createTable(boolean useplainconf) throws Exception {
                HBaseAdmin admin;
                // This is the way that doesn't work (I don't know why yet)
                // It fails with
                if (useplainconf) {
                        admin = new HBaseAdmin(new Configuration());
                }
                else {
                        admin = new HBaseAdmin(HBaseConfiguration.create());
                }

		if (admin.tableExists(TEST_TABLE_BYTES)) {
                        admin.disableTable(TEST_TABLE_BYTES);
                        admin.deleteTable(TEST_TABLE_BYTES);
                        // throw new Exception("Table already exists.  Won't delete");
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
                boolean useplainconf = false;
                System.out.println("args:  " + args);
                if (args.length > 0) {
                        useplainconf = true;
                }

                System.out.println("Use plain conf is:  " + useplainconf);
                try {
                        CreateTable ct = new CreateTable();
                        ct.createTable(useplainconf);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}
