hdfs dfs -mkdir -p /dualcore/ads
hdfs dfs -put ads.txt /dualcore/ads
impala-shell -f create_ads.sql
impala-shell -f analyze_ads.sql
