package com.learnkafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learnkafka.model.Response;
import com.learnkafka.model.StudentInputModel;
import com.learnkafka.producer.StudentProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    private StudentProducer studentProducer;


    @PostMapping("/insertStudentDetails")
    @Operation(summary = "Create Student", tags = "StudentProducer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Accepted", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "503",description = "Service Unavailable")
    })
    public ResponseEntity<?> saveStudentDetails(@RequestBody StudentInputModel studentDto) throws JsonProcessingException {
        System.out.println("enter saveStudentDetails "+studentDto);

        ResponseEntity<?> validate = validateStudentDetails(studentDto);
        if(validate!= null){
            return ResponseEntity.badRequest().body(validate.getBody());
        }
        studentProducer.postStudentInputRequest(studentDto);

        return ResponseEntity.ok(new Response(0,"created"));

    }


    private static ResponseEntity<Map<String,Object>> validateStudentDetails(StudentInputModel studentDto) {
        Map<String,String> getAllErrors =validateInputParameters(studentDto);
        if(!getAllErrors.isEmpty()){
            Map<String,Object> body = new LinkedHashMap<>();
            body.put("Errors",getAllErrors);
            return ResponseEntity.badRequest().body(body);
        }
        return null;
    }

    private static Map<String,String> validateInputParameters(StudentInputModel studentDto) {
        Map<String,String> getAllErrors = new HashMap<>();
        if(studentDto.getName() == null || "".equals(studentDto.getName())) {
            getAllErrors.put("name", "Student name cannot be null");
        }
        if(studentDto.getEmail() == null || "".equals(studentDto.getEmail())) {
            getAllErrors.put("Email ", "Student email cannot be null");
        }
        if(studentDto.getBook() == null || "".equals(studentDto.getBook())) {
            getAllErrors.put("Book ", "Student book cannot be null");
        }
        return getAllErrors;
    }
}
