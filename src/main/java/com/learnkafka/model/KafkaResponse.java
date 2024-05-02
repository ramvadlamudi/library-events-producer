package com.learnkafka.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KafkaResponse implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String[] brokers;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String topic;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String topicsDetails;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String kafkaClusterStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String brokerDetails;

    public String[] getBrokers() {
        return brokers;
    }

    public void setBrokers(String[] brokers) {
        this.brokers = brokers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicsDetails() {
        return topicsDetails;
    }

    public void setTopicsDetails(String topicsDetails) {
        this.topicsDetails = topicsDetails;
    }

    public String getKafkaClusterStatus() {
        return kafkaClusterStatus;
    }

    public void setKafkaClusterStatus(String kafkaClusterStatus) {
        this.kafkaClusterStatus = kafkaClusterStatus;
    }

    public String getBrokerDetails() {
        return brokerDetails;
    }

    public void setBrokerDetails(String brokerDetails) {
        this.brokerDetails = brokerDetails;
    }
}
