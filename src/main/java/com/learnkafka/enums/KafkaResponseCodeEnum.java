package com.learnkafka.enums;

public enum KafkaResponseCodeEnum {

    NO_KAFKA_CLUSTER_CONNECTED(1, "Error Occurred while communicating with Kafka Cluster"),
    BROKER_DETAILS_IN_KAFKA_CLUSTER(2,"Broker Details are not available"),
    ALL_TOPIC_DETAILS_IN_KAFKA_CLUSTER(3,"All Topics are not available"),
    TOPIC_DETAILS_AVAILABLE_IN_KAFKA_CLUSTER(4,"The Clncl Event Topic is available"),
    TOPIC_DETAILS_NOT_AVAILABLE_IN_KAFKA_CLUSTER(5,"The Clncl Event Topic is not available");


    private Integer id;
    private String description;

    KafkaResponseCodeEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
