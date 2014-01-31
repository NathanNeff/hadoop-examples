USE tpcds_sample;

-- From 
-- http://www.cloudera.com/content/cloudera-content/cloudera-docs/Impala/latest/Installing-and-Using-Impala/ciiu_rcfile.html?scroll=rcfile_compression_unique_1
SET hive.exec.compress.output=true;
SET mapred.max.split.size=256000000;
SET mapred.output.compression.type=BLOCK;
SET mapred.output.compression.codec=org.apache.hadoop.io.compress.SnappyCodec;

INSERT OVERWRITE TABLE rc_store_sales SELECT * FROM store_sales;
INSERT OVERWRITE TABLE seq_store_sales SELECT * FROM store_sales;
