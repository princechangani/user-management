package com.campus_connect.user_management.controller;


import com.campus_connect.user_management.ClientService.StudentClientService;
import com.campus_connect.user_management.DTO.AdminDto;
import com.campus_connect.user_management.DTO.FacultyDto;
import com.campus_connect.user_management.DTO.StudentDto;
import com.campus_connect.user_management.responce.FeesDto;
import com.campus_connect.user_management.service.AdminService;
import com.campus_connect.user_management.service.FacultyService;
import com.campus_connect.user_management.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/campus-connect/admin")
public class AdminController {
    private final StudentService studentService;
    private  final FacultyService facultyService;
    private final AdminService adminService;
    private final StudentClientService studentClientService;
    public AdminController(StudentService studentService, FacultyService facultyService, AdminService adminService, StudentClientService studentClientService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
        this.adminService = adminService;
        this.studentClientService = studentClientService;
    }

    @PostMapping("/all/admins")
    public List<AdminDto> getAllUsers(@RequestBody AdminDto adminDto) {
        return adminService.getAllAdmins(adminDto);
    }
    @PostMapping("/register-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminDto saveAdmin(@RequestBody AdminDto adminDTO,@RequestHeader("Authorization") String token) {
        String tokenWithoutBearer = token.replace("Bearer ", "");

        adminService.saveAdmin(adminDTO,tokenWithoutBearer.trim());
        return adminDTO ;
    }
@PostMapping( "/register-faculty")
    @ResponseStatus(HttpStatus.CREATED)
    public FacultyDto saveFaulty(@RequestBody FacultyDto facultyDto,@RequestHeader("Authorization") String token) {
    String tokenWithoutBearer = token.replace("Bearer ", "");
    facultyService.saveFaculty(facultyDto,tokenWithoutBearer.trim());
    return facultyDto;
}
    @PostMapping( "/register-student")
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDto saveStudent(@RequestBody StudentDto studentDto,@RequestHeader("Authorization") String token) {
        String tokenWithoutBearer = token.replace("Bearer ", "");
        System.out.println("Authorization token "+tokenWithoutBearer);
        studentService.saveStudent(studentDto,tokenWithoutBearer.trim());

        return studentDto;
    }
        @PostMapping("/login")
    public ResponseEntity<AdminDto> verifyUser(@RequestBody AdminDto adminDto) {
        AdminDto verifiedUser = adminService.verify(adminDto);
        return new ResponseEntity<>(verifiedUser, HttpStatus.ACCEPTED);
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


    @PostMapping("/save/fees")
    @ResponseStatus(HttpStatus.OK)
    public FeesDto getStudentFee(@RequestBody FeesDto feesDto) {
        return studentClientService.saveStudentFees(feesDto);
    }



}
