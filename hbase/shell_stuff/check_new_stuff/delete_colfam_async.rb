tbl = 'nate_alter'

if @hbase.admin(@formatter).exists?(tbl)
        puts "Table '#{tbl}' already exists.  Please drop it first."
        exit 1
end

create tbl, { NAME => 'cf1' }, { NAME => 'cf2' }
put tbl, 1, 'cf1:col1', 'value1'
put tbl, 1, 'cf2:col2', 'value1'

scan tbl

puts "Now deleting cf2"
alter tbl, NAME => 'cf2', METHOD => 'delete'

scan tbl

puts "*" * 10, "Watch this, we can delete only remaining colfam"
alter tbl, NAME => 'cf1', METHOD => 'delete'
scan tbl
describe tbl

disable tbl
drop tbl
exit
