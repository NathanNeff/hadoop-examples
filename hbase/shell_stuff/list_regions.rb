tbl = get_table('njn_transactions')
# Get to the underlying tbl.table for the REAL power, my apprentice.
puts "Here's the regions:"
tbl.table.get_region_locations.each_with_index do |region_thingy, idx|
        puts "-" * 100
        puts "Region " + idx.to_s
        # Still haven't figured out what class region_thingy is but oh well.
        # puts region_thingy[0].class
        puts region_thingy[0].get_region_name_as_string
        puts Bytes.toString(region_thingy[0].regionName)

        # Print out info about this region
        puts region_thingy[0].toString()
end

puts "Now printing start keys of each region in this table:  "
tbl.table.get_start_keys.each do |byte_array_start_key|
        puts Bytes.toString(byte_array_start_key)
end
exit
