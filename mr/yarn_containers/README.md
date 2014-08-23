# Container Memory Allocation

These are the properties that I had to change/verify to make sure
that NodeManagers could allocate, for example 8 containers for
512 MB maps, and 4 containers for 1024 MB maps

```xml
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
```

Also, you can set

  ``mapreduce.map.memory.mb`` or ``mapreduce.map.reduce.mb`` on a per-job basis like this:
  
    hadoop jar ./SleepJob.jar SleepJob -Dmapreduce.map.memory.mb=1024 -m 100 -r 10 -mt 240000

Or, set the defaults in the mapred-site.xml.

And, of course, if you really want the JVMs to actually use the memory or not
use it, you must specify:

    mapreduce.map.java.opts     # (Default is 200MB!!)
    mapreduce.reduce.java.opts  # (Default is 200MB!!)

# Container Memory Management

See ./run_memory_container_kill.sh and SleepJobWithArray.java for how YARN kills
tasks which request more memory than their containers have.  The SleepJobWithArray simply
tries to instantiate an array of ints that is greater than the container's memory size.

The weird thing is that I can't get YARN to kill the Java process simply because it
tries to start with an Xmx (Or Xms) that's greater than the container's memory size.

The java process actually has to have the code that requests > memory than the YARN container
has.  Notice that in SleepJobWithArray, it requests an array of 512 MB, which is under
the Java Heap Size that's requested Xmx=1024m.

