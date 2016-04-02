http://www.cloudera.com/documentation/enterprise/latest/topics/cdh_sg_hbase_authorization.html

Tested with CDH 5.7

## Add this to /etc/hbase/conf/hbase-site.xml

<property>
     <name>hbase.security.authorization</name>
     <value>true</value>
</property>
<property>
     <name>hbase.coprocessor.master.classes</name>
     <value>org.apache.hadoop.hbase.security.access.AccessController</value>
</property>
<property>
     <name>hbase.coprocessor.region.classes</name>
     <value>org.apache.hadoop.hbase.security.token.TokenProvider,org.apache.hadoop.hbase.security.access.AccessController</value>
</property>

## Restart HBase

for i in hbase-master hbase-regionserver hbase-rest hbase-thrift; 
do sudo service $i restart; 
done

## Open two hbase-shells, in different terminals

    Terminal1: $ hbase shell


    Terminal2: $ sudo -u hbase hbase shell

   - In terminal 1, attempt to create a table

   - In terminal 2, attempt to create a table


## In Terminal 2 (hbase user)

	hbase> whoami
	hbase> create 't1', 'cf1'

# Grant 'RW' access to 't1' to the 'cloudera' or 'training' user (depending on VM) 

	hbase> grant 'cloudera', 'RW', 't1'

# Grant global 'RW' access to everything

	hbase> grant 'cloudera', 'RW'

# Create namespace and table in namespace

	hbase> create_namespace 'ns1'
	hbase> create 'ns1:t1', 'cf1'
	hbase> grant 'RW', 'cloudera', 'ns1:t1'

## Investigate permissions 

	hbase> help 'user_permission'
	hbase> user_permission 't1'
	hbase> user_permission '@ns1' 

## Revoke global permissions for cloudera

        revoke 'cloudera', 'RW'
        # In terminal 2, you can still select from t1 as Cloudera.
