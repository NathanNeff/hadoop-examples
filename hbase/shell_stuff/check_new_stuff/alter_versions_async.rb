tbl = "test_movie"
disable tbl
drop tbl
if @hbase.admin(@formatter).exists?(tbl)
        puts "Table #{tbl} already exists. Please drop or use different table"
        exit 1
end

create tbl, { NAME => 'desc' }
put tbl, 'Star Wars', 'desc:title', 'Star Wars'
put tbl, 'Star Wars', 'desc:title', 'Star Wars:A New Hope'

get tbl, 'Star Wars', { COLUMNS => 'desc:title', VERSIONS => 2 }

alter_async tbl, NAME => 'desc', VERSIONS => 2
put tbl, 'Star Wars', 'desc:title', 'Star Wars:A New New Hope'
get tbl, 'Star Wars', { COLUMNS => 'desc:title', VERSIONS => 2 }

puts "Checking alter status"
alter_status tbl

disable tbl
drop tbl
