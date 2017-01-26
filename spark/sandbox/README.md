# Sandbox

A "real" Spark project used for demonstrating simple, basic
ideas.

# Running Locally
When running locally (OSX), provide an absolute path to the
application (e.g. /Users/bob/src/hadoop-examples/sandbox/resources/input.txt

# Snappy native-library (running locally)
When running locally on OSX and using sc.textFile, the following error
might be encountered:

    Caused by: java.lang.UnsatisfiedLinkError: no snappyjava in java.library.path
    at java.lang.ClassLoader.loadLibrary(ClassLoader.java:1878)
    at java.lang.Runtime.loadLibrary0(Runtime.java:849)
    at java.lang.System.loadLibrary(System.java:1087)
    at org.xerial.snappy.SnappyNativeLoader.loadLibrary(SnappyNativeLoader.java:52)

There's a "better way" to handle this problem, which is to upgrade the version
of snappy in the maven pom.  But currently, this project's pom does not have
that fix in it.  Contributions welcome :)

http://stackoverflow.com/questions/30039976/unsatisfiedlinkerror-no-snappyjava-in-java-library-path-when-running-spark-mlli#30040003

Adding this line to the JVM options when running the LogLevel.scala 
will also fix the problem (but again it's probably best to mod the pom.xml)

	-Dorg.xerial.snappy.lib.name=libsnappyjava.jnilib -Dorg.xerial.snappy.tempdir=/tmp 
