# Create HBase Table
def drop_if_exists_and_create(name, *args)
  if @hbase.admin(@formatter).exists?(name.to_s)
    @hbase.admin(@formatter).disable name
    @hbase.admin(@formatter).drop name
    puts "Droppped table: " + name
  end
  
  @hbase.admin(@formatter).create name, *args
  puts "Created table: " + name + "\n\n"
end

drop_if_exists_and_create 'nate_hbase_sales_grouped', { NAME => 'cf1' }
drop_if_exists_and_create 'nate_hbase_movie_ratings', { NAME => 'ratings' }

put 'nate_hbase_movie_ratings', 'nate', 'ratings:star_wars', '5'
put 'nate_hbase_movie_ratings', 'nate', 'ratings:clone_wars', '1'

put 'nate_hbase_movie_ratings', 'steve', 'ratings:star_wars', '5'
put 'nate_hbase_movie_ratings', 'steve', 'ratings:clone_wars', '1'

put 'nate_hbase_movie_ratings', 'dumbo', 'ratings:star_wars', '1'
put 'nate_hbase_movie_ratings', 'dumbo', 'ratings:clone_wars', '5'

put 'nate_hbase_movie_ratings', 'suzie', 'ratings:beaches', '4'
put 'nate_hbase_movie_ratings', 'suzie', 'ratings:magnolia', '4'

exit
