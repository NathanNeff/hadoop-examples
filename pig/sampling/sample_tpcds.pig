sales = LOAD 'tpcds/store_sales';
sampl = SAMPLE sales 0.01;
STORE sampl INTO 'tpcds_sample/store_sales';
