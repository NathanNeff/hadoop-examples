# HDFS Cheatsheet

Show the contents of /loudacre/weblogs.

```
hdfs dfs -ls /loudacre/weblogs
-rw-rw-rw-   1 training supergroup     539134 2016-07-26 11:11 /loudacre/weblogs/somefile.txt
-rw-rw-rw-   1 training supergroup     509467 2016-07-26 11:11 /loudacre/weblogs/another.txt
```
The information shown is:

- Permissions (-rw-rw-rw)
- Replication Factor (1)
- Owner/Group (training/supergroup)
- File size (538134)
- Modification Date/Time (2016-07-26 11:11)

Create a directory

```
hdfs dfs -mkdir /dualcore 
hdfs dfs -mkdir documents
```

The difference between =/dualcore= and =documents= above is =/dualcore=
will be created at the /root/ direcotry and =documents= will be created
in the user's /home/ directory.  Typically a user's home directory in HDFS
is =/user/bob/= where bob is the username.

View the contents of a file in HDFS:

```
hdfs dfs -cat /earcloud/somefile.txt
```

The contents of the file will be printed to the terminal.  If the file is very
large, you can use Ctrl-C to stop the command.  If you know the file is very
large, you can =hdfs dfs -tail= to view only the last X lines of the file.

```
hdfs dfs -tail /earcloud/somefile.txt
```

Retrieve the contents of an HDFS file to the local machine:

```
# This will download somefile.txt from HDFS to the local machine
hdfs dfs -get /earcloud/somefile.txt

# Download somefile.txt to the local machine and name it another.txt
hdfs dfs -get /earcloud/somefile.txt another.txt
```

Delete a directory in HDFS *Warning* potential to delete lots of data!

```
hdfs dfs -rm -R /earcloud/customers
```

For more information, see "HDFS Filesystem Shell" at
https://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/FileSystemShell.html)
at Apache.org  

