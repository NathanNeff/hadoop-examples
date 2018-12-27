package com.cloudera.kafkaexamples;

import java.util.Collection;
import java.util.Map.Entry;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeekToBeginningListener implements ConsumerRebalanceListener {
    static private final Logger logger = LoggerFactory.getLogger(SeekToBeginningListener.class);


    private KafkaConsumer<?,?> kc;
    
    public SeekToBeginningListener(KafkaConsumer<?,?> kc) {
        this.kc = kc;
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        logger.debug("Partitions Reassigned");
        for (Entry<TopicPartition, Long> tpAndOffset : kc.beginningOffsets(partitions).entrySet()) {
            
            String msg = 
                    String.format("Topic: %s, Partition: %d, Beginning Offset: %d", 
                            tpAndOffset.getKey().topic(),
                            tpAndOffset.getKey().partition(),
                            tpAndOffset.getValue());
                            
            logger.debug(msg);
            
        }
        for (Entry<TopicPartition, Long> tpAndOffset : kc.endOffsets(partitions).entrySet()) {
            
            String msg = 
                    String.format("Topic: %s, Partition: %d, Current Offset: %d", 
                            tpAndOffset.getKey().topic(),
                            tpAndOffset.getKey().partition(),
                            tpAndOffset.getValue());
                            
            logger.debug(msg);
            
        }

        kc.seekToBeginning(partitions);
    }

}
