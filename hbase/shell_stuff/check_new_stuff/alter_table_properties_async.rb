tbl = "test_movie"
if @hbase.admin(@formatter).exists?(tbl)
        puts "Table #{tbl} already exists. Please drop or use different table"
        exit 1
end

create tbl, { NAME => 'desc'}, { READONLY => 'true' }
put tbl, 'Star Wars', 'desc:title', 'Star Wars'

alter_async tbl, READONLY => 'false'
put tbl, 'Star Wars', 'desc:title', 'Star Wars'

disable tbl
drop tbl
