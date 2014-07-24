require 'test/unit'
extend Test::Unit::Assertions

tbl = get_table('njn_transactions')
# Get to the underlying tbl.table for the REAL power, my apprentice.
puts "Here's the regions:"
# get_region_locations returns a "NavigableMap" Java object that has a RegionInfo as a key, and ServerName as value
# http://archive.cloudera.com/cdh5/cdh/5/hbase-0.96.1.1-cdh5.0.1/devapidocs/org/apache/hadoop/hbase/client/HTable.html#getRegionLocations%28%29
tbl.table.get_region_locations.each_with_index do |region_thingy, idx|
        puts "-" * 100
        puts "Region " + idx.to_s

        # get_region_name_as_string is the same thing as .regionName(), except String vs. Byte Array
        regionName = region_thingy[0].get_region_name_as_string
        assert_equal regionName, Bytes.toString(region_thingy[0].regionName)

        # Print out info about this region
        puts "Region Name is: " + regionName
        puts region_thingy[0].toString()
end

puts "Now printing start keys of each region in this table:  "
tbl.table.get_start_keys.each do |byte_array_start_key|
        puts Bytes.toString(byte_array_start_key)
end
exit
