package com.campus_connect.user_management.StudentClientRepository;

import com.campus_connect.user_management.responce.ResultDto;
import com.campus_connect.user_management.responce.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "result-service", url = "http://localhost:7777/student")
public interface ResultClient {
    @GetMapping("/result/{enrollmentNo}")
    StudentResponse getStudentResult(@PathVariable("enrollmentNo") Long enrollmentNo);
    @PostMapping("/result")
    ResultDto saveResult(@RequestBody ResultDto resultDto);
}
