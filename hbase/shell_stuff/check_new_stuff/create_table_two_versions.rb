# Create tbl w/two versions
tbl = 'test_two_vers'
create tbl, {NAME => 'desc', VERSIONS => 2}
describe tbl

put tbl, 'Empire', 'desc:title', 'Empire Wimps Out'
put tbl, 'Empire', 'desc:title', 'Empire Strikes Back'

get tbl, 'Empire', { COLUMN=>'desc:title', VERSIONS=> 2}

disable tbl
drop tbl

exit
