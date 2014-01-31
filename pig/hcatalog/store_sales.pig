salez = LOAD 'tpcds.store_sales' USING org.apache.hcatalog.pig.HCatLoader(); 
DESCRIBE salez;
