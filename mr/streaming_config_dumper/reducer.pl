#!/bin/env perl
# Get all key value pairs, and just concatenate them together
use strict;
use warnings;
my $curr_key;
my $prev_key;
my $curr_val;
my $val;

while(<>) {
        ($curr_key, $curr_val) = split /\t/;
        if ($prev_key && ($curr_key ne $prev_key)) {
                print $prev_key, "\t", $val, "\n";
                $prev_key = $curr_key;
                $val = $curr_val;
        }
        else {
                $prev_key = $curr_key;
                $val .= " $curr_val";
        }
}
if ($curr_key) {
        print $curr_key, "\t", $val, "\n";
}

