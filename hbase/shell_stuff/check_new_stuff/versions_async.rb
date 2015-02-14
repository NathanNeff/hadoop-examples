tbl = 'nate_alter'

if @hbase.admin(@formatter).exists?(tbl)
        puts "Table '#{tbl}' already exists.  Please drop it first."
        exit 1
end

create tbl, { NAME => 'cf1' }

put tbl, '1', 'cf1:col1', 'value'
put tbl, '1', 'cf1:col1', 'value'
put tbl, '1', 'cf1:col1', 'value'
put tbl, '1', 'cf1:col1', 'value'

puts "*" * 10, "Get row -- we see 1 version is kept."
get tbl, '1', { COLUMN => 'cf3:col3', VERSIONS => 5 }

puts "*" * 10, "Now, alter versions to 5"
alter tbl, NAME => 'cf3', VERSIONS => '5'

put tbl, '1', 'cf1:col1', 'value'
put tbl, '1', 'cf1:col1', 'value'
put tbl, '1', 'cf1:col1', 'value'
put tbl, '1', 'cf1:col1', 'value'
puts "*" * 10, "Now we have more versions retained"
get tbl, '1', { COLUMN => 'cf1:col1', VERSIONS => 5 }

disable tbl
drop tbl
