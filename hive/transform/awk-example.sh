#!/bin/bash
echo "BEFORE:  "
cat ../sample-data/transform-example.txt 
echo "AFTER AWK FILTER"
cat ../sample-data/transform-example.txt | awk '! a[$1]++'
