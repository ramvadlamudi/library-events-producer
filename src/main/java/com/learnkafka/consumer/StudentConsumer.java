package com.learnkafka.consumer;

import com.learnkafka.model.StudentRequest;
import com.learnkafka.producer.StudentProducer;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Data
@Service
public class StudentConsumer {

    private static final Logger log= LoggerFactory.getLogger(StudentConsumer.class);

    //@KafkaListener(topics = "#{__listener.topicName}",containerFactory = "kafkaListenerContainerFactory",id = "#{__listener.groupId}")
    @KafkaListener(topics = "test-topic1", groupId = "jt-group")
    public void onMessage(ConsumerRecord<String, StudentRequest> consumerRecord, Acknowledgment ack) {
        StudentRequest initialRequest = consumerRecord.value();
        log.info("initial request: [{}]",initialRequest);
        ack.acknowledge();
   }
}
