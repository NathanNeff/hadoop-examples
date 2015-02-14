tbl = "test_movie"
if @hbase.admin(@formatter).exists?(tbl)
        puts "Table #{tbl} already exists. Please drop or use different table"
        exit 1
end

create tbl, { NAME => 'desc', VERSIONS => 3 }
put tbl, 'rowkey1', 'desc:title', 'New Hope'
put tbl, 'rowkey1', 'desc:year', '1975', 1
put tbl, 'rowkey1', 'desc:year', '1976', 2
put tbl, 'rowkey1', 'desc:year', '1977', 3

put tbl, 'rowkey2', 'desc:title', 'Empire Strikes Back'
put tbl, 'rowkey2', 'desc:year', '1975', 1
put tbl, 'rowkey2', 'desc:year', '1976', 2
put tbl, 'rowkey2', 'desc:year', '1980', 3

put tbl, 'rowkey3', 'desc:title', 'Return of the Jedi'
put tbl, 'rowkey3', 'desc:year', '1975'
put tbl, 'rowkey3', 'desc:year', '1976'
put tbl, 'rowkey3', 'desc:year', '1982'

puts "We have all three rows"
scan tbl

delete tbl, 'rowkey3', 'desc:year'
puts "No Jedi years should be visible here"
scan tbl, { STARTROW => 'rowkey3' }

puts "No Empire year before 1980 should be here"
delete tbl, 'rowkey2', 'desc:year', 2
scan tbl, { STARTROW => 'rowkey2', ENDROW => 'rowkey3', VERSIONS => 3 }

puts "No Star Wars rows should be here"
deleteall tbl, 'rowkey1'
scan tbl

puts "No more rows should be here:"
truncate tbl
scan tbl

disable tbl
drop tbl

exit

