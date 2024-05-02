package com.learnkafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnkafka.model.StudentInputModel;
import com.learnkafka.model.StudentRequest;
import com.learnkafka.service.MonitorService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Data
@Component
public class StudentProducer {

    private static final Logger log= LoggerFactory.getLogger(StudentProducer.class);

    @Autowired
    @Qualifier("studentRequestTemplate")
    KafkaTemplate<String,StudentRequest> kafkaTemplate;

    ObjectMapper objectMapper;

    public void postStudentInputRequest(StudentInputModel studentInputModel) throws JsonProcessingException {
        this.postStudentDetailsRequest(studentInputModel);
    }

    public void postStudentDetailsRequest(StudentInputModel studentInputModel) throws JsonProcessingException {
        StudentRequest request = new StudentRequest(studentInputModel.getId(),studentInputModel.getName(),studentInputModel.getEmail(),studentInputModel.getBook());
        log.info("Posting Student request: {}",request);
        postStudentRequest(request);
    }

    public void postStudentRequest(StudentRequest request) throws JsonProcessingException {
        String topic = "test-topic1";
        String key = request.getId().toString();

        ListenableFuture<SendResult<String,StudentRequest>> listenableFuture = kafkaTemplate.send(topic,key,request);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, StudentRequest>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, request, ex);
            }

            @Override
            public void onSuccess(SendResult<String, StudentRequest> result) {
                handleSuccess(key, request);
            }
        });

    }

    public void handleFailure(String key,StudentRequest value,Throwable ex ){
        try {
            throw new KafkaException("Encountered an error while produceing student message "+ value.getId(),ex);

        } catch (Throwable e) {
            log.error("Error in handleFailure for message {}: {}",value.getId(),e.getMessage() );
        }
    }

    public void handleSuccess (String key,StudentRequest value) {
        log.info("Student request  Sent successfully for the key : {} and the value is {}",key,value);
    }


}
