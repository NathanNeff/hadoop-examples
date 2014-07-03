import org.apache.hadoop.hbase.client.HBaseAdmin

admin = HBaseAdmin.new(@hbase.configuration)
puts admin.getTableNames().to_a
exit
