package com.campus_connect.user_management.controller;

import com.campus_connect.user_management.DTO.AdminDto;
import com.campus_connect.user_management.DTO.FacultyDto;
import com.campus_connect.user_management.DTO.StudentDto;
import com.campus_connect.user_management.service.AdminService;
import com.campus_connect.user_management.service.FacultyService;
import com.campus_connect.user_management.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/campus-connect/faculty")
public class FacultyController {
    private final StudentService studentService;
    private  final FacultyService facultyService;
    private final AdminService adminService;
    public FacultyController(StudentService studentService, FacultyService facultyService, AdminService adminService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
        this.adminService = adminService;
    }

   /* @PostMapping("/all/faculty")
    public List<FacultyDto> getAllUsers(@RequestBody FacultyDto facultyDto) {
        return facultyService.(facultyDto);
    }*/

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> verifyUser(@RequestBody FacultyDto facultyDto) {
        FacultyDto verifiedUser = facultyService.verify(facultyDto);

        Map<String, Object> loginResponse = new HashMap<>();
        loginResponse.put("status", "success");

        loginResponse.put("bearerToken",verifiedUser.getBearerToken());
        return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
    }
    @GetMapping("/all/student")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> getAllStudent() {
        return studentService.getAllStudents();
    }

    @GetMapping("/all/faculty")
    @ResponseStatus(HttpStatus.OK)
    public List<FacultyDto> getAllFaculty() {
        return facultyService.getAllFaculties();
    }
    @GetMapping("/all/admin")
    @ResponseStatus(HttpStatus.OK)
    public List<AdminDto> getAllAdmins() {
        return adminService.getAllAdmins();
    }

}
