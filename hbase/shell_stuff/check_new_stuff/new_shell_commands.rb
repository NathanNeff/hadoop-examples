# Tested create and acceptance of VERSIONS parameter
disable 'nate_movie'
drop 'nate_movie'

# 04-08 Shell Command Syntax
create 'nate_movie', {NAME => 'desc', VERSIONS => 5}

# Verification
put 'nate_movie', 1, 'desc:title', 'Star Wars'
put 'nate_movie', 1, 'desc:title', 'Star Wars version 2'
put 'nate_movie', 1, 'desc:title', 'Star Wars version 3'
put 'nate_movie', 1, 'desc:title', 'Star Wars version 4'
put 'nate_movie', 1, 'desc:title', 'Star Wars version 5'

get 'nate_movie', '1', {COLUMN=>'desc', VERSIONS=>3}

# Pass parameters test
if ARGV.length()
get ARGV[1], ARGV[2]
