package util;

import java.io.IOException;
import java.util.Observable;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
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

	public void blast(String tableName, int numRecords) {

		try {
			HTableInterface tbl = connection.getTable(TableName
					.valueOf(tableName));

			long startTime = System.currentTimeMillis();
			for (int timez = 0; timez < numRecords - 1; timez++) {
				
				// System.out.println("Before");
				this.setChanged();
				this.notifyObservers(new Integer(timez));
				// System.out.println("After");
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
