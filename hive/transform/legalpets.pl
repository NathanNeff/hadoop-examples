#!/bin/env perl
%legal = qw/dog 1
            cat 1
            ferret 1
            bird 1
	    chimpanzee 1/;

my @petsIveSeen = ();
while ($pet = <>) {
	chomp($pet);
	# debug -- this goes to /var/log/hadoop/userlogs/<job>/<task>/stderr
	print STDERR $pet;
	if ($legal{$pet}) {
		print "$pet\tYES\n";
	}
	else {
		print "$pet\tNO\n";
	}	

        push(@petsIveSeen, $pet);
}
# debug -- this goes to /var/log/hadoop/userlogs/<job>/<task>/stderr
print STDERR join(',', @petsIveSeen);
