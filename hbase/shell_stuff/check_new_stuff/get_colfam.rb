tbl = "test_movie"
if @hbase.admin(@formatter).exists?(tbl)
        puts "Table #{tbl} already exists. Please drop or use different table"
        exit 1
end

create tbl, { NAME => 'desc' }, { NAME => 'ratings' }
put tbl, 'Star Wars', 'desc:title', 'New Hope'
put tbl, 'Star Wars', 'desc:year', '1977', 1274032629664
put tbl, 'Star Wars', 'desc:year', '1978', 1274032629663
put tbl, 'Star Wars', 'ratings:bob', '1'
put tbl, 'Star Wars', 'ratings:steve', '5'


puts "Getting data:  We should only see data from desc colfam\n:" +
     "And we should see 1977 because it has a later timestamp"
get tbl, 'Star Wars', { COLUMN => 'desc' }

disable tbl
drop tbl

exit

