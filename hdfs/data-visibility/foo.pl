#!/bin/env perl
for($i=0;  $i<=1_000_000; $i++) {
        if ($i % 10000 == 0) {
                sleep(1);
                print "Sleeping " . `date`;
        }
        print $i, "\n";
}
