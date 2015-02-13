# Thankfully, invalid/unknown arguments cause HBase Shell
# to print a warning
create 'sometable', { NAME => 'cf1', VERSION => 1, VERSIONS => 2 }
disable 'sometable'
drop 'sometable'
exit
