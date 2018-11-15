# Running these

I like the Maven exec plugin:

    mvn exec:java -Dexec.mainClass="com.cloudera.kafkaexamples.SimpleProducer"

Also kinda cool to override the log4j properties at runtime:

    mvn exec:java \
        -Dexec.mainClass="com.cloudera.kafkaexamples.SimpleProducer" \
        -Dlog4j.configuration="file:/full/path/to/THIS_IS_COOL.properties"
