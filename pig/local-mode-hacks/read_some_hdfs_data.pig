hdfs_data = LOAD 'THISISINHDFS.txt';
grpd = GROUP hdfs_data ALL;
counted = FOREACH grpd GENERATE COUNT(hdfs_data);
DUMP counted;

