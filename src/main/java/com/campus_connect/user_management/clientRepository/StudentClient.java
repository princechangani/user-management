package com.campus_connect.user_management.clientRepository;

import com.campus_connect.user_management.responce.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "student-service", url = "http://localhost:7777/student")
public interface StudentClient {

    @GetMapping("/result/{enrollmentNo}")
    StudentResponse getStudentResult(@PathVariable("enrollmentNo") Long enrollmentNo);
    @PostMapping("/result")
    ResultDto saveResult(@RequestBody ResultDto resultDto);



    @GetMapping("/schedule/get-by-sem-div")
    List<ScheduleDto> getScheduleBySemAndDiv(@RequestParam Integer semester, @RequestParam String division);



    @PostMapping("/fees/save")
    FeesDto saveFees(@RequestBody FeesDto feesDto);
    @GetMapping("/fees/get/{enrollmentNo}")
    List<FeesDto> getStudentFees(@PathVariable Long enrollmentNo);



    @PostMapping("/attendance/save")
    AttendanceDto saveAttendance(@RequestBody AttendanceDto attendanceDto);
    @GetMapping("/attendance/{enrollmentNo}")
    AttendanceDto getAttendance(@PathVariable Long enrollmentNo);




    @GetMapping("/profile/get/{enrollmentNo}")
    ProfileDto getProfile(@PathVariable Long enrollmentNo);
    @GetMapping("/profile/save")
    ProfileDto saveProfile(@RequestBody ProfileDto profileDto);
}



