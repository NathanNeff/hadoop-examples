if ARGV && ARGV.length() == 2
        tbl = ARGV[0]
        colfam = ARGV[1]
        create tbl, colfam
        describe tbl
else
        puts "Usage:  parameter_create_tbl.rb <table> <colfam>"
        exit 1
end
exit 0
