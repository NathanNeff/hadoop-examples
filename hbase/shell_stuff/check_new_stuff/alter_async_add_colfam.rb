tbl = 'nate_alter'

if @hbase.admin(@formatter).exists?(tbl)
        puts "Table '#{tbl}' already exists.  Please drop it first."
        exit 1
end

create tbl, { NAME => 'cf1' }
put tbl, '1', 'cf1:col1', 'value'
alter tbl, NAME => 'cf2'
put tbl, '1', 'cf2:col2', 'value'
alter tbl, NAME => 'cf3'
put tbl, '1', 'cf3:col3', 'value'
describe tbl
scan tbl

disable tbl
drop tbl
