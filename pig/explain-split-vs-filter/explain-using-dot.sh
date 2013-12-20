#!/bin/bash
sudo yum install graphviz
pig -x local -e 'explain -script ./using-filter.pig' -dot -out using-filter.dot
pig -x local -e 'explain -script ./using-split.pig' -dot -out using-split.dot
for fil in *dot; do
        # output all graphs in the *dot files (there's three per .dot file)
        # automagically generate filenames ($fil.1.pdf, $fil.2.pdf, etc)
        # enjoy
        dot -Tpdf $fil -O
done
