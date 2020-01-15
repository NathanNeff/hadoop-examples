# First, create / massage all possible data sources
# ----- Accounts
# Create a DataFrame based on the Hive accounts table
accountsDF = spark.read.table("devsh.accounts")\
                 .select("acct_num", "acct_close_dt")\
                 .withColumnRenamed("acct_num", "account_id")

accountsDF.createOrReplaceTempView("accounts")

# ----- Account Devices
# Load accountdevice data to HDFS in another terminal window
# $ hdfs dfs -put $DEVDATA/accountdevice/ /devsh_loudacre/
accountDeviceDF = spark.read.option("header","true").\
                             option("inferSchema","true")\
                             .csv("/devsh_loudacre/accountdevice")

accountDeviceDF.createOrReplaceTempView("account_devices")

# ----- Devices
devicesDF = spark.read.json("/devsh_loudacre/devices.json").withColumnRenamed("devnum", "device_id")
devicesDF.createOrReplaceTempView("devices")

# Spark SQL!!!!!

sql = """
SELECT d.device_id, d.make, d.model, COUNT(*) AS number_of_devices
FROM accounts a
JOIN account_devices ad ON a.account_id = ad.account_id
JOIN devices d ON d.device_id = ad.device_id
WHERE a.acct_close_dt IS NULL
GROUP BY d.device_id, d.make, d.model
ORDER BY number_of_devices DESC
"""

activeDeviceCountsDF = spark.sql(sql)
activeDeviceCountsDF.write\
    .mode("overwrite")\
    .option("path","/devsh_loudacre/active_device_counts_using_spark_sql")\
    .saveAsTable("devsh.active_device_counts_using_spark_sql")
