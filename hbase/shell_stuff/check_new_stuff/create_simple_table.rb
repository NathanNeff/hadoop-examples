tbl = 'test_simple'

create tbl, {NAME => 'desc'}
describe tbl
put tbl, 'Star Wars', 'desc:title', 'Star Wars'
get tbl, 'Star Wars'

disable tbl
drop tbl
exit
