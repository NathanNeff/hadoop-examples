hdfs dfs -mkdir -p /dualcore/ads
hdfs dfs -put ads.txt /dualcore/ads
impala-shell -f create_ads.sql
impala-shell -f lag_ads.sql -o lag_ads.txt
impala-shell -f avg_ads.sql -o avg_ads.txt
echo "Look in lag_ads.txt, and avg_ads.txt"
