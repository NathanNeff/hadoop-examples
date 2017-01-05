# HBase Simple Authorization Example

http://www.cloudera.com/documentation/enterprise/latest/topics/cdh_sg_hbase_authorization.html

Tested with CDH 5.7, using the [Cloudera Quickstart VM](http://www.cloudera.com/downloads/quickstart_vms.html)

## Enable Simple Authorization

Add the XML below to `/etc/hbase/conf/hbase-site.xml`

```xml
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
```

Restart HBase

```shell
$ for svc in hbase-master hbase-regionserver hbase-rest hbase-thrift; 
do 
    sudo service $svc restart; 
done
```

## Test Simple Authorization

For the remaining tests, we will open two shells and grant/revoke/test
access.  Open an hbase shell as the default user (`cloudera` is the default
user in the Quickstart VM), and open an hbase shell as the `hbase` user.

**Terminal #1**

    $ hbase shell

**Terminal #2**

    $ sudo -u hbase hbase shell

Attempt to create a table/database as the default user in Terminal #1.  You
should receive an AccessDeniedException

**Terminal #1 (default user)**

    hbase> whoami
    cloudera (auth:SIMPLE)
        groups: cloudera, default

    # Attempt to create a table
    hbase> create 't1', 'cf1'

    org.apache.hadoop.hbase.security.AccessDeniedException: Insufficient
    permissions for user 'cloudera' (global, action=ADMIN)

    # Attempt to create a namespace
    hbase> create_namespace 'ns1', 'cf1'

    org.apache.hadoop.hbase.security.AccessDeniedException: Insufficient
    permissions for user 'cloudera' (global, action=ADMIN)

Attempt to create a table as user "hbase"

**Terminal #2 (hbase user)**

    hbase> whoami
    hbase (auth:SIMPLE)
        groups: hbase

    hbase> create 't1', 'cf1'
    0 row(s) in 0.0350 seconds

    hbase> create_namespace 'ns1'
    0 row(s) in 0.0350 seconds

    hbase> create 'ns1:t1', 'cf1'
    0 row(s) in 1.2330 seconds

    # Add data to the tables
    hbase> put 't1', 'rowkey1', 'cf1:f1', '1'
    0 row(s) in 1.2330 seconds

    hbase> put 'ns1:t1', 'rowkey1', 'cf1:f1', '1'
    0 row(s) in 1.2330 seconds

**Terminal 1 (default user)**

Attempt to put data into `t1` and `ns1:t1` should fail.

    hbase> put 't1', 'rowkey2', 'cf1:f1', '2'

    ERROR: Failed 1 action:
    org.apache.hadoop.hbase.security.AccessDeniedException: Insufficient
    permissions (user=cloudera, scope=t1, family=cf1:f1,
    params=[table=t1 family=cf1:f1],action=WRITE)


    hbase> put 'ns1:t1', 'rowkey2', 'cf1:f1', '2'

    ERROR org.apache.hadoop.hbase.security.AccessDeniedException: Insufficient
    permissions (user=cloudera, scope=ns1:t1, family=cf1:f1,
    params=[table=ns1:t1 family=cf1:f1],action=WRITE)

**Terminal 2 (hbase user)**

Grant access for default user (in this case it's cloudera)

    grant 'cloudera', 'RW', 't1'
    grant 'cloudera', 'RW', 'ns1:t1'

**Terminal 1**

Attempt the PUTs again in Terminal 1.  They should succeed.

**Terminal 2 (hbase user)**

Revoke the permissions

    hbase> revoke 'cloudera', 't1'        # 'cloudera' user should now receive AccessDenied
    hbase> revoke 'cloudera', '@ns1:t1'   # 'cloudera' user should now receive AccessDenied

Attempt the PUTs in Terminal 1.  They should FAIL.  Try revoking one privilege,
but not the other.


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
