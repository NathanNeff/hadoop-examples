# Run this file using:
#     hbase shell hbase_blocks.rb
# - Try changing hbase_block_size to different values.  When block_size decreases,
#   the number of records in the index INcreases
# - Try changing the size of each record (record_size below).  As the size of the records increase, the gaps
#   between the keys in the index INcreases
#   TODO: Write code to check the Block Cache size after some GETs :)

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.HBaseAdmin

num_records = 1000
hbase_block_size = 128000
record_size = 10000

admin = HBaseAdmin.new(Configuration.new())

table_name = 'blocksize_test'
bytes_table_name = TableName.valueOf(table_name)

if admin.tableExists(bytes_table_name)
	if  admin.isTableEnabled(bytes_table_name)
        admin.disableTable(bytes_table_name)
    end
	admin.deleteTable(bytes_table_name)
end

# Use standard shell command to create table
create table_name, { NAME => 'info', BLOCKSIZE => hbase_block_size }

1.upto(num_records) do |row_key|
 	put table_name, row_key, 'info:name', 'customer ' + row_key.to_s
 	put table_name, row_key, 'info:data', 'X' * record_size
end

flush table_name
 
# Get Region Name for region 1
first_region = admin.getTableRegions(bytes_table_name)[0]
region_name = first_region.getRegionNameAsString()
region_encoded_name = first_region.getEncodedName()

puts "Table #{table_name} created."
puts "\tNumber of records: #{num_records}"
puts "\tRecord Size:       #{record_size}"
puts "\tHBase Block Size:  #{hbase_block_size}"

# Print all regions just to show how to loop through
# regions.  "ri" means RegionInfo (See API)
puts "Here's the regions:\n"
admin.getTableRegions(bytes_table_name).each do |ri|
	puts "\t" + ri.getRegionNameAsString()
    puts 
end

msg = "To see the index/block information, run the command below in the Linux shell:\n\n" +
      "hbase hfile --printblocks -r #{region_name}"

puts msg
exit
