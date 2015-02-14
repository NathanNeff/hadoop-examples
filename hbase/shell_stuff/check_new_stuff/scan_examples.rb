tbl = "test_movie"
if @hbase.admin(@formatter).exists?(tbl)
        puts "Table #{tbl} already exists. Please drop or use different table"
        exit 1
end

create tbl, { NAME => 'desc' }, { NAME => 'media' }
put tbl, 'rowkey1', 'desc:title', 'New Hope'
put tbl, 'rowkey1', 'media:type', 'Tape'

put tbl, 'rowkey2', 'desc:title', 'Empire Strikes Back'
put tbl, 'rowkey2', 'desc:year', '1980'
put tbl, 'rowkey2', 'media:type', 'Tape'

put tbl, 'rowkey3', 'desc:title', 'Jedi'
put tbl, 'rowkey3', 'media:type', 'Tape'

put tbl, 'rowkey4', 'desc:title', 'Phantom'
put tbl, 'rowkey4', 'media:type', 'DVD'

put tbl, 'rowkey5', 'desc:title', 'Clone'
put tbl, 'rowkey5', 'media:type', 'DVD'


scan tbl

puts "limiting to 1"
scan tbl, { LIMIT => 1 }

puts "startrow of rowkey1, end of rowkey4"
scan tbl, { STARTROW => 'rowkey1', STOPROW => 'rowkey4' }

puts "only retrieve title and type fields"
scan tbl, { COLUMNS => [ 'desc:title', 'media:type' ] }

disable tbl
drop tbl

exit

