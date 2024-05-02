package com.learnkafka.service;

import com.learnkafka.enums.KafkaResponseCodeEnum;
import com.learnkafka.model.KafkaResponse;
import com.learnkafka.model.KafkaStatusResponse;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@Service
public class MonitorService {

    private static final Logger log= LoggerFactory.getLogger(MonitorService.class);

    public KafkaStatusResponse checkKafkaClusterConnectivity()   {
        log.info("Verify Kafka Cluster Connectivity");
        KafkaStatusResponse kafkaStatusResponse = new KafkaStatusResponse();
        KafkaResponse kafkaResponse = new KafkaResponse();
        try {
            Map<String, Object> configProp = new HashMap<>();
            String topic = "test-topic1";
            configProp.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            //configProp.put(AbstractKafkaSchemaSerDeConfig.AUTO_ReGISTER_SCHEMAS,false);
            //configProp.put("schema.registry.url", "http://localhost:9812");

            AdminClient adminClient = AdminClient.create(configProp);
            if (adminClient != null) {
                DescribeClusterResult clusterResult = adminClient.describeCluster();
                String clusterId = clusterResult.clusterId().get();
                if (clusterId != null) {
                    kafkaResponse.setKafkaClusterStatus("Reachable");
                    Collection<Node> brokerDetails = clusterResult.nodes().get();
                    if (brokerDetails != null && !brokerDetails.isEmpty()) {
                        List<String> nodeList = brokerDetails.stream().map(broker -> {
                            return broker.host() + ":" + broker.port();
                        }).collect(Collectors.toList());
                        kafkaResponse.setBrokers(nodeList.stream().toArray(String[]::new));
                        Collection<TopicListing> topicListings = adminClient.listTopics(new ListTopicsOptions().listInternal(true)).listings().get();
                        if (topicListings != null && !topicListings.isEmpty()) {
                            Set<String> topics = adminClient.listTopics(new ListTopicsOptions().listInternal(true)).names().get();
                            if (topics != null && !topics.isEmpty()) {
                                if (topics.contains(topic)) {
                                    kafkaResponse.setTopic(new StringBuilder().append(topic).append(" ").append("is available").toString());
                                    return new KafkaStatusResponse(KafkaResponseCodeEnum.TOPIC_DETAILS_AVAILABLE_IN_KAFKA_CLUSTER,kafkaResponse);
                                } else {
                                    kafkaResponse.setTopic(new StringBuilder().append(topic).append(" ").append("is not available").toString());
                                    return new KafkaStatusResponse(KafkaResponseCodeEnum.TOPIC_DETAILS_NOT_AVAILABLE_IN_KAFKA_CLUSTER,kafkaResponse);
                                }

                            } else {
                                kafkaResponse.setTopicsDetails("All topics are not Available");
                                return new KafkaStatusResponse(KafkaResponseCodeEnum.ALL_TOPIC_DETAILS_IN_KAFKA_CLUSTER,kafkaResponse);
                            }
                        }

                    } else {
                        kafkaResponse.setBrokerDetails("Broker Details are not available");
                        return new KafkaStatusResponse(KafkaResponseCodeEnum.BROKER_DETAILS_IN_KAFKA_CLUSTER,kafkaResponse);
                    }

                }
            }
        } catch (ExecutionException | InterruptedException e) {
            kafkaResponse.setKafkaClusterStatus("Kafka cluster is not available");
            return new KafkaStatusResponse(KafkaResponseCodeEnum.NO_KAFKA_CLUSTER_CONNECTED,kafkaResponse);
        }
        return kafkaStatusResponse;
    }

    public KafkaStatusResponse getKafkaConnectionDetails() {
        return checkKafkaClusterConnectivity();
    }
}
