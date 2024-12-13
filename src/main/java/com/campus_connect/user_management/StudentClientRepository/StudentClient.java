package com.campus_connect.user_management.StudentClientRepository;

import com.campus_connect.user_management.responce.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "student-service", url = "http://localhost:7777")
public interface StudentClient {

    @PostMapping("/student/result")
    StudentResponse saveResult(@RequestBody StudentResponse studentResponse);
}



