package com.campus_connect.user_management.StudentClientRepository;


import com.campus_connect.user_management.responce.AttendanceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "attendance-service", url = "http://localhost:7777/student/attendance")
public interface AttendanceClient {

    @PostMapping("/save")
    AttendanceDto saveAttendance(@RequestBody AttendanceDto attendanceDto);
    @GetMapping("/{enrollmentNo}")
    AttendanceDto getAttendance(@PathVariable Long enrollmentNo);

}
