tbl = "test_movie"
if @hbase.admin(@formatter).exists?(tbl)
        puts "Table #{tbl} already exists. Please drop or use different table"
        exit 1
end

create tbl, { NAME => 'desc' }, { NAME => 'media' }

put tbl, 'Star Wars', 'desc:duration', 'binary:120'
put tbl, 'Empire', 'desc:duration', 100
put tbl, 'Jedi', 'desc:duration', '120'

# Binary tells the filter what kind of comparator to use
scan tbl, { FILTER => "SingleColumnValueFilter('desc', 'duration', =, 'binary:120')" }

disable tbl
drop tbl

exit

