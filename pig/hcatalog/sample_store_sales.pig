salez = LOAD 'tpcds.parquet_store_sales' USING org.apache.hcatalog.pig.HCatLoader(); 
sampld = SAMPLE salez 0.1;
STORE sampld INTO 'zanky';
