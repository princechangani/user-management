package com.campus_connect.user_management.StudentClientRepository;


import com.campus_connect.user_management.responce.ScheduleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "schedule-service", url = "http://localhost:7777/student/schedule")
public interface ScheduleClient {
    @GetMapping("get-by-sem-div")
    List<ScheduleDto> getScheduleBySemAndDiv(@RequestParam Integer semester, @RequestParam String division);

}
