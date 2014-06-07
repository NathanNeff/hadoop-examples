These are the properties that I had to change/verify to make sure
that NodeManagers could allocate, for example 8 containers for
512 MB maps, and 4 containers for 1024 MB maps

<property>
        <name>yarn.nodemanager.resource.memory-mb</name>
        <value>4096</value>
        <source>yarn-site.xml</source>
</property>

<property>
        <name>yarn.scheduler.minimum-allocation-mb</name>
        <value>512</value>
        <source>yarn-site.xml</source>
</property>

<property>
        <name>yarn.nodemanager.resource.cpu-vcores</name>
        <value>8</value>
        <source>yarn-site.xml</source>
</property>

Also, you can set

mapreduce.map.memory.mb and/or
mapreduce.map.reduce.mb on a per job basis like this:

hadoop jar ./SleepJob.jar SleepJob -Dmapreduce.map.memory.mb=1024 -m 100 -r 10 -mt 240000

Or, set the defaults in the mapred-site.xml

And, of course, if you really want the JVMs to actually
use the memory or not use it, you must specify

mapreduce.map.java.opts (Default is 200MB!!)
mapreduce.reduce.java.opts (Default is 200MB!!)



