# Running these

I like the Maven exec plugin:

    mvn exec:java -Dexec.mainClass="com.cloudera.kafkaexamples.SimpleProducer"

Also kinda cool to override the log4j properties at runtime:

    mvn exec:java \
        -Dexec.mainClass="com.cloudera.kafkaexamples.SimpleProducer" \
        -Dlog4j.configuration="file:/full/path/to/THIS_IS_COOL.properties"

## Running the From-Beginning Example

You need to supply TOPIC and BOOTSTRAP_SERVERS environment variable(s)

    export TOPIC=customers
    export BOOTSTRAP_SERVERS=
    mvn exec:java \
        -Dexec.mainClass="com.cloudera.kafkaexamples.SimpleConsumer" \
        -Dexec.args="--from-beginning --group-id foogroup --bootstrap-server $BOOTSTRAP_SERVERS --topic $TOPIC" \
        -Dlog4j.configuration="file:./log4jConfigs/seekToBeginning.properties"
