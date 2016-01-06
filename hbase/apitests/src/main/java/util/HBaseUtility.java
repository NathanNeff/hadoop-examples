package util;

import java.io.IOException;
import java.util.Observable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtility extends Observable {

	private static final long serialVersionUID = 1L;
	HConnection connection;
	Configuration config;

	public HBaseUtility() {
		config = HBaseConfiguration.create();

		try {
			connection = HConnectionManager.createConnection(config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Hack that "truncates" a table by saving the description,
	// deleting the table, then re-creating it.
	public void truncateTable(String tableName) {
		HBaseAdmin admin;
		try {

			admin = new HBaseAdmin(connection);
		} catch (MasterNotRunningException | ZooKeeperConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		// Delete the table
		System.out.println("Preparing to delete table " + tableName + "\n");
		System.out.println("... Disabling HBase table: " + tableName + "\n");

		try {
			admin.disableTable(tableName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Boolean isDisabled;
		try {
			isDisabled = admin.isTableDisabled(tableName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				admin.close();
			} catch (IOException io) {
				io.printStackTrace();
			}
			return;
		}

		if (true == isDisabled) {
			System.out.println("... Table " + tableName
					+ " successfully disabled.\n");
			System.out.println("... Deleting table " + tableName + "\n");
			try {

				HTableInterface tbl = connection.getTable(tableName);
				HTableDescriptor tblDesc = tbl.getTableDescriptor();
				admin.deleteTable(tableName);
				admin.createTable(tblDesc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					admin.close();
				} catch (IOException io) {
					io.printStackTrace();
				}
			}
			System.out.println("\nTable: " + tableName
					+ " successfully deleted.\n");
		} else {
			System.out.println("ERROR: Table " + tableName
					+ " disable failed.\n");
			System.out.println("\n");
		}
		try {
			admin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void checkAndPut(String tablename) {

		// Create a table for testing with
		final byte[] CF_CF1 = Bytes.toBytes("cf1");

		final byte[] FIRST_NAME_V1 = Bytes.toBytes("Jokey");
		final byte[] LAST_NAME_V1 = Bytes.toBytes("Smurf");

		HTableInterface tbl;
		try {
			tbl = connection.getTable(TableName.valueOf(tablename));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}

		// Put a row
		final byte[] COL_FIRST_NAME = Bytes.toBytes("firstname");
		final byte[] COL_LAST_NAME = Bytes.toBytes("lastname");

		byte[] rowKey = Bytes.toBytes("rowkey1");

		Put p1 = new Put(rowKey);

		p1.add(CF_CF1, COL_FIRST_NAME, FIRST_NAME_V1);

		boolean res1;
		try {
			res1 = tbl.checkAndPut(rowKey, CF_CF1, COL_FIRST_NAME, null, p1);
			System.out.println("\nFirst name was put? : " + res1 + "\n"); // true
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		boolean res2;
		try {
			res2 = tbl.checkAndPut(rowKey, CF_CF1, COL_FIRST_NAME, null, p1);
			System.out.println("\nFirst name was put again? : " + res2 + "\n"); // false
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		// Try putting a different field using Check and Put
		// If the first name field contains the first value
		Put p2 = new Put(rowKey);
		p2.add(CF_CF1, COL_LAST_NAME, LAST_NAME_V1);

		boolean res3;
		try {

			res3 = tbl.checkAndPut(rowKey, CF_CF1, COL_FIRST_NAME,
					FIRST_NAME_V1, p2);
			System.out
					.println("\nLast Name was put after checking first name? : "
							+ res3 + "\n"); // true
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

	}

	public void blast(String tableName, int numRecords) {

		try {
			HTableInterface tbl = connection.getTable(TableName
					.valueOf(tableName));

			long startTime = System.currentTimeMillis();
			for (int timez = 0; timez < numRecords - 1; timez++) {

				System.out.println("Before");
				this.setChanged();
				this.notifyObservers(new Integer(timez));
				System.out.println("After");
				Put p = new Put(Bytes.toBytes(Long.toString(timez + startTime)));
				p.add(Bytes.toBytes("cf1"), Bytes.toBytes("col1"),
						Bytes.toBytes(timez));
				tbl.put(p);
			}
		} catch (Exception e) {
			System.err.println(e);

		}

	}

}
