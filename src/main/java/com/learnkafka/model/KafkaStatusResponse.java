package com.learnkafka.model;

import com.learnkafka.enums.KafkaResponseCodeEnum;

import java.io.Serializable;

public class KafkaStatusResponse implements Serializable {

    private Integer respCode;
    private KafkaResponse kafkaResponse;

    public KafkaStatusResponse() {
    }

    public KafkaStatusResponse(KafkaResponseCodeEnum respCode, KafkaResponse kafkaResponse) {
        this.respCode = respCode.getId();
        this.kafkaResponse = kafkaResponse;
    }

    public Integer getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public KafkaResponse getKafkaResponse() {
        return kafkaResponse;
    }

    public void setKafkaResponse(KafkaResponse kafkaResponse) {
        this.kafkaResponse = kafkaResponse;
    }
}
