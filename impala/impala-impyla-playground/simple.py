from impala.dbapi import connect
conn = connect(host='localhost', port=21050)
cur = conn.cursor()
cur.execute("""
CREATE EXTERNAL TABLE IF NOT EXISTS simple(id INT, name STRING)
	ROW FORMAT DELIMITED
	FIELDS TERMINATED BY '\t'
	STORED AS TEXTFILE
	LOCATION '/user/cloudera/tables/simple';
""")

cur.execute("SELECT * FROM simple");
results = cur.fetchall()
assert 41 == len(results);


