package hadoop.examples.kafka;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SimpleProducer {
    public static void main(String[] args) {
        // Generate total consecutive events starting with ufoId
        long total = 100000L;
        long ufoId = 0L;

        // Set up client Java properties
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "quickstart.cloudera:9092");

        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.ACKS_CONFIG, "1");

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {

            for (long i = 0; i < total; i++) {
                String key = Long.toString(ufoId++);
                long runtime = new Date().getTime();
                double latitude = (Math.random() * (2 * 85.05112878)) - 85.05112878;
                double longitude = (Math.random() * 360.0) - 180.0;
                String msg = key + "," + latitude + "," + longitude;
                try {
                    ProducerRecord<String, String> data = new ProducerRecord<String, String>("ufo_sightings", key, msg);
                    producer.send(data);
                    // long wait = Math.round(Math.random() * 25);
                    // Thread.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
