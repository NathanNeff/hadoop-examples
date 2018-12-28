package com.cloudera.kafkaexamples;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class SimpleConsumer {
    Options options = new Options();
    CommandLine cmd = null;
    Properties props = new Properties();

    private static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrapservers";
    private static final String FROM_BEGINNING_CONFIG = "frombeginning";
    private static final String GROUP_ID_CONFIG = "groupid";
    private static final String TOPIC_CONFIG = "topic";

    private void configure() {

        // Set up client properties according to commandline
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, cmd.getOptionValue(BOOTSTRAP_SERVERS_CONFIG));
        if (null != cmd && cmd.hasOption(GROUP_ID_CONFIG)) {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, cmd.getOptionValue(GROUP_ID_CONFIG));
        } else {
            props.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        }

        // Set up hard-coded properties :(
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    }

    public void parseArgs(String[] args) throws ParseException {

        Option topicOption = Option.builder(TOPIC_CONFIG)
                .longOpt("topic")
                .required(true)
                .desc("Topic to subscribe to")
                .hasArg(true).argName("topic").build();

        options.addOption(topicOption);

        Option bootstrapOption = Option.builder(BOOTSTRAP_SERVERS_CONFIG)
                .longOpt("bootstrap-server")
                .desc("Kafka brokers to use in bootstrap")
                .required(true)
                .hasArg(true)
                .argName("bootstrap-server")
                .build();
        options.addOption(bootstrapOption);

        Option fromBeginningOption = Option.builder(FROM_BEGINNING_CONFIG)
                .longOpt("from-beginning")
                .desc("Read topic from beginning")
                .hasArg(false)
                .argName("from-beginning").build();

        options.addOption(fromBeginningOption);

        Option groupOption = Option.builder(GROUP_ID_CONFIG)
                .longOpt("group-id")
                .desc("Consumer Group to Join")
                .hasArg(true)
                .argName("Consumer Group to Join (default is a UUID)").build();

        options.addOption(groupOption);
        CommandLineParser parser = new DefaultParser();
        cmd = parser.parse(options, args);
    }

    public void startConsuming() {
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            if (null != cmd && cmd.hasOption(FROM_BEGINNING_CONFIG)) {
                /*
                 * This program does two things when "from-beginning" is specified:
                 * 
                 * 1) What to do if there are no tracked offsets for a particular group or
                 * partition. "earliest", "latest", and none which will throw an exception.
                 * Setting "--from-beginning" tells this program to opt for "earliest" offset of
                 * a topic/partition if there is no tracked offset for this group.
                 * 
                 * 2) Seek To Beginning - We use an event listener which seeksToBeginning upon
                 * topic/partition assignments. This is confusing versus using
                 * seekToBeginning(partitions). The problem is we have not been assigned any
                 * partitions yet, so seekToBeginning doesn't work without calling poll(0) See
                 * https://grokbase.com/t/kafka/users/16384874pk/seektobeginning-doesnt-work-
                 * without-auto-offset-reset
                 */
                props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

                System.out.println("Will seek to beginning, when topics/partitions are assigned.");
                consumer.subscribe(Arrays.asList(cmd.getOptionValue(TOPIC_CONFIG)),
                        new SeekToBeginningListener(consumer));
            } else {
                consumer.subscribe(Arrays.asList(cmd.getOptionValue(TOPIC_CONFIG)));
            }

            while (true) {
                try {
                    ConsumerRecords<String, String> records = consumer.poll(100);
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.printf("Offset = %d\n", record.offset());
                        System.out.printf("Key    = %s\n", record.key());
                        System.out.printf("Value  = %s\n", record.value());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        SimpleConsumer sc = new SimpleConsumer();
        try {
            sc.parseArgs(args);
        } catch (ParseException pe) {
            System.err.println(pe.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            PrintWriter pw = new PrintWriter(System.err);
            formatter.printHelp(pw, 120, "SimpleConsumer", "", sc.options, 5, 10, "");
            pw.close();
            System.exit(1);
        }
        sc.configure();
        sc.startConsuming();
    }
}
