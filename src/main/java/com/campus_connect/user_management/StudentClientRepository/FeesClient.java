package com.campus_connect.user_management.StudentClientRepository;


import com.campus_connect.user_management.responce.FeesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "fees-service", url = "http://localhost:7777/student/fees")
public interface FeesClient {
    @PostMapping("/save")
    FeesDto saveFees(@RequestBody FeesDto feesDto);
    @GetMapping("/get/{enrollmentNo}")
    List<FeesDto> getStudentFees(@PathVariable Long enrollmentNo );
}
