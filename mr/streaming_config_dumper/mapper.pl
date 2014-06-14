#!/bin/env perl
# Just print this mapper's env vars
while(<>) {} # <--- this is weird, the scripts won't finish (They'll crash with "Broken Pipe" errors unless you close STDIN explicitly
             # or use a while(<>) {}
foreach $key(keys(%ENV)) {
        print $key, "\t", $ENV{$key}, "\n";
}
print STDERR "I've fallen and can't get up! + $ENV{map_input_file}\n";
