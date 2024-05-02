package com.learnkafka.config;

import com.learnkafka.model.StudentRequest;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.security.auth.SecurityProtocol;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import javax.crypto.EncryptedPrivateKeyInfo;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Slf4j
public class KafkaProducerConfig {

    private String overrideServer;

    @Bean
    public ProducerFactory<String, StudentRequest> producerFactory() {
        Map<String,Object> configProps = new HashMap<>();
        String bootstrapAddress = "localhost:9092";
        String userName = "KAFKA_USERNAME";
        String password = "KAFKA_PASS";
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //configProp.put(AbstractKafkaSchemaSerDeConfig.AUTO_ReGISTER_SCHEMAS,false);
       // configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        //configProps.put(ProducerConfig.RETRIES_CONFIG,10);
        //configProps.put("security.protocal", SecurityProtocol.SASL_SSL.name);
        //configProps.put("sasl.mechanisam", "PLAIN");
        //configProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username='"+ userName + "' password='"+ password +"';");
        //configProp.put("schema.registry.url", "http://localhost:8081");

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, StudentRequest> studentRequestTemplate(ProducerFactory<String,StudentRequest> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public NewTopic createTopic(){
        return new NewTopic("test-topic",3,(short)1);
    }



    public String getOverrideServer() {
        return overrideServer;
    }

    public void setOverrideServer(String overrideServer) {
        this.overrideServer = overrideServer;
    }
}
