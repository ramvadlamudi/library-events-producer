package com.learnkafka.controller;

import com.learnkafka.model.KafkaStatusResponse;
import com.learnkafka.service.MonitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class MonitorController {

    @Autowired
    private MonitorService monitorService;

    @GetMapping("/kafkahealthcheck")
    @Operation(summary = "get kafka status check", tags = "ProducerBroker")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "503",description = "Service Unavailable")
    })
    public ResponseEntity<?> kafkaHealthCheck(){
        KafkaStatusResponse kafkaStatusResponse = monitorService.getKafkaConnectionDetails();
        if(kafkaStatusResponse == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            if(kafkaStatusResponse.getRespCode() ==1 || kafkaStatusResponse.getRespCode() == 2
            || kafkaStatusResponse.getRespCode() ==3 || kafkaStatusResponse.getRespCode() ==5){
                return new ResponseEntity<>(kafkaStatusResponse,HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(kafkaStatusResponse,HttpStatus.OK);
        }

    }
}
