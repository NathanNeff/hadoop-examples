# README

Illustrate that a file need not be a full HDFS block before 
data can be read from the file.

1.  Run this in a shell:

    ./foo.pl | hadoop fs -put - data.txt

2.  Open another shell and run this

    hadoop fs -ls data.txt._COPYING_
    hadoop fs -cat data.txt._COPYING_ | head -n 10

3. *Note* Don't forget to Ctrl-C ./foo.pl!!!!
