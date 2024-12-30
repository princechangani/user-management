package com.campus_connect.user_management.controller;

import com.campus_connect.user_management.ClientService.StudentClientService;
import com.campus_connect.user_management.DTO.AdminDto;
import com.campus_connect.user_management.DTO.FacultyDto;
import com.campus_connect.user_management.DTO.StudentDto;
import com.campus_connect.user_management.OTP.EmailService;
import com.campus_connect.user_management.OTP.OTPService;
import com.campus_connect.user_management.OTP.OTPStorage;
import com.campus_connect.user_management.OTP.SmsService;
import com.campus_connect.user_management.exception.UnauthorizedAccessException;
import com.campus_connect.user_management.responce.*;
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




    @PostMapping("/send-otp")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> sendOTP(@RequestBody StudentDto studentDto) {
        studentService.sendOTP(studentDto);
        return new ResponseEntity<>("OTP sent", HttpStatus.OK);
    }

    @PostMapping("/login/{otp}")
    public ResponseEntity< Map<String, Object>> verifyStudent(@RequestBody StudentDto studentDto,@PathVariable String otp) {
        Map<String, Object> loginResponse = new HashMap<>();


        if (OTPStorage.validateOTP(studentDto.getEmail(),otp)) {
         try   {
                StudentDto verifiedStudent = studentService.verify(studentDto);
                loginResponse.put("status", "success");
                loginResponse.put("bearerToken", verifiedStudent.getBearerToken());
            }
         catch (Exception e) {
             throw new UnauthorizedAccessException(e.getMessage());
         }

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

    @PostMapping("save/attendance/{enrollmentNo}")
    @ResponseStatus(HttpStatus.CREATED)
    public AttendanceDto saveStudentAttendance(@PathVariable Long enrollmentNo, @RequestBody AttendanceDto attendanceDto){
        return studentClientService.saveStudentAttendance(enrollmentNo,attendanceDto);

    }
    @GetMapping("get/attendance/{enrollmentNo}")
    @ResponseStatus(HttpStatus.OK)
    public AttendanceDto getStudentAttendance(@PathVariable Long enrollmentNo){
        return studentClientService.getStudentsAttendance(enrollmentNo);

    }

    @PutMapping("update/{enrollmentNo}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto updateStudent(@PathVariable Long enrollmentNo, @RequestBody StudentDto studentDto) {
        return studentService.updateStudent(studentDto, enrollmentNo);
    }

    @GetMapping("{email}/{enrollmentNo}")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getStudentEmail(@PathVariable String email,@PathVariable Long enrollmentNo){
        Map<String, Object> response = new HashMap<>();
        response.put("student", studentService.getStudentByEmail(email.trim()));
        response.put("attendance", studentClientService.getStudentsAttendance(enrollmentNo));
        return response;
    }

    @GetMapping("get/schedule/{semester}-{division}")
    public List<ScheduleDto> getStudentSchedule(@PathVariable Integer semester,@PathVariable String division){
        return studentClientService.getStudentSchedule(semester,division);
    }

    @GetMapping("get/fees/{enrollmentNO}")
    @ResponseStatus(HttpStatus.OK)
    public List<FeesDto> getStudentFee(@PathVariable Long enrollmentNO){
        return studentClientService.getStudentFees(enrollmentNO);
    }

}

