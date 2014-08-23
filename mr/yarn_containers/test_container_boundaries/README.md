# Container Memory Management

See ./run.sh and SleepJobWithArray.java for how YARN kills
tasks which request more memory than their containers have.  The SleepJobWithArray simply
tries to instantiate an array of ints that is greater than the container's memory size.

The weird thing is that I can't get YARN to kill the Java process simply because it
tries to start with an Xmx (Or Xms) that's greater than the container's memory size.

The java process actually has to have the code that requests > memory than the YARN container
has.  Notice that in SleepJobWithArray, it requests an array of 512 MB, which is under
the Java Heap Size that's requested Xmx=1024m.

