# Shorthand
tbl = 'test_shorthand'
create tbl, 'movie', 'desc', 'media'
put tbl, 'Phantom', 'desc:title', 'Phantom Menace'
put tbl, 'Phantom', 'media:thumbs_down', 'thumbs_down'
get tbl, 'Phantom'

disable tbl
drop tbl

exit
