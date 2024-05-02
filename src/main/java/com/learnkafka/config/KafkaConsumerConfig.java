package com.learnkafka.config;

import com.learnkafka.consumer.StudentConsumer;
import com.learnkafka.model.StudentRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties
@Slf4j
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, StudentRequest> consumerFactory() {
        Map<String, Object> configProp = new HashMap<>();
        String bootstrapAddress = "localhost:9092";
        configProp.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProp.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProp.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configProp.put(JsonDeserializer.TRUSTED_PACKAGES, "com.learnkafka.model");
       // configProp.put(ConsumerConfig.GROUP_ID_CONFIG, "my-first-application");
        //configProp.put(AbstractKafkaSchemaSerDeConfig.AUTO_ReGISTER_SCHEMAS,false);

        //configProps.put("security.protocal", SecurityProtocol.SASL_SSL.name);
        //configProps.put("sasl.mechanisam", "PLAIN");
        //configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='"+ userName + "' password='"+ password +"';");
        //configProp.put("schema.registry.url", "http://localhost:8081");
        return new DefaultKafkaConsumerFactory<>(configProp);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, StudentRequest> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, StudentRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(2);
        factory.setAutoStartup(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }


}
