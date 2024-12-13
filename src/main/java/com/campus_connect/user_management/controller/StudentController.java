package com.campus_connect.user_management.controller;

import com.campus_connect.user_management.ClientService.StudentClientService;
import com.campus_connect.user_management.DTO.AdminDto;
import com.campus_connect.user_management.DTO.FacultyDto;
import com.campus_connect.user_management.DTO.StudentDto;
import com.campus_connect.user_management.OTP.EmailService;
import com.campus_connect.user_management.OTP.OTPService;
import com.campus_connect.user_management.OTP.OTPStorage;
import com.campus_connect.user_management.OTP.SmsService;
import com.campus_connect.user_management.responce.AttendanceDto;
import com.campus_connect.user_management.responce.ResultDto;
import com.campus_connect.user_management.responce.StudentResponse;
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
@RequestMapping("/campus-connect/student")
public class StudentController {
    private final StudentClientService studentClientService;
    private final OTPService otpService;
    private final SmsService smsService;
    private final EmailService emailService;

    private final StudentService studentService;
    private  final FacultyService facultyService;
    private final AdminService adminService;
    public StudentController(StudentClientService studentClientService, OTPService otpService, SmsService smsService, EmailService emailService, StudentService studentService, FacultyService facultyService, AdminService adminService) {
        this.studentClientService = studentClientService;
        this.otpService = otpService;
        this.smsService = smsService;
        this.emailService = emailService;
        this.studentService = studentService;
        this.facultyService = facultyService;
        this.adminService = adminService;
    }




    @PostMapping("/send-otp/{contact}")
    public ResponseEntity<String> sendOTP(@PathVariable String contact) {
        String otp = otpService.generateOTP();
        try {
            if (contact.contains("@")) {
                emailService.sendOTP(contact, otp);
            } else {
                smsService.sendOTP(contact, otp);
            }
            OTPStorage.storeOTP(contact, otp);
            return ResponseEntity.ok("OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending OTP");
        }
    }

    @PostMapping("/login/{otp}")
    public ResponseEntity< Map<String, Object>> verifyStudent(@RequestBody StudentDto studentDto,@PathVariable String otp) {


        if (OTPStorage.validateOTP(studentDto.getEmail(),otp)) {
            StudentDto verifiedStudent = studentService.verify(studentDto);
            Map<String, Object> loginResponse = new HashMap<>();
            loginResponse.put("status", "success");
            loginResponse.put("bearerToken",verifiedStudent.getBearerToken());
            return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);

        }


        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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

    @GetMapping("/result")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse getStudentResult (@RequestParam("enrollmentNo") Long enrollmentNo){
        return studentClientService.getStudentResult(enrollmentNo);
    }
    @PostMapping("save/result")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultDto saveStudentResult (@RequestParam("enrollmentNo") Long enrollmentNo, @RequestBody ResultDto studentResponse){
        return studentClientService.saveStudentResult(enrollmentNo,studentResponse);
    }

    @PostMapping("save/attendance")
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceDto saveStudentAttendance(@RequestParam("enrollmentNo") Long enrollmentNo, @RequestBody AttendanceDto attendanceDto){
        return studentClientService.saveStudentAttendance(enrollmentNo,attendanceDto);

    }
    @GetMapping("get/{enrollmentNo}")
    @ResponseStatus(HttpStatus.OK)
    public AttendanceDto getStudentAttendance(@PathVariable Long enrollmentNo){
        return studentClientService.getStudentsAttendance(enrollmentNo);

    }


}
