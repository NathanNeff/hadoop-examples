# Create two colfams
tbl = 'test_two_colfams'

create tbl, {NAME => 'desc'}, {NAME => 'media'}
describe tbl

put tbl, 'Jedi', 'desc:title', 'Return of the Jedi'
put tbl, 'Jedi', 'media:fanboy_picture', 'fanboy\'s picture'

get tbl, 'Jedi'

disable tbl
drop tbl
exit

