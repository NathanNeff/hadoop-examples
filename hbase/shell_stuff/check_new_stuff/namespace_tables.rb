puts "Simple table"
create_namespace 'entertainment'
create 'entertainment:movie', { NAME => 'desc' }
disable 'entertainment:movie'
drop 'entertainment:movie'

puts "Versions = 2"
create 'entertainment:movie', { NAME => 'desc', VERSIONS => 2 }
disable 'entertainment:movie'
drop 'entertainment:movie'

puts "Two colfams"
create 'entertainment:movie', { NAME => 'desc', VERSIONS => 2 }
disable 'entertainment:movie'
drop 'entertainment:movie'

# Only empty namespaces can be removed
drop_namespace 'entertainment'
