package com.campus_connect.user_management.service;

import com.campus_connect.user_management.DTO.StudentDto;
import com.campus_connect.user_management.DataEntity.Student;
import com.campus_connect.user_management.OTP.EmailService;
import com.campus_connect.user_management.OTP.OTPService;
import com.campus_connect.user_management.OTP.OTPStorage;
import com.campus_connect.user_management.OTP.SmsService;
import com.campus_connect.user_management.Repository.StudentRepository;

import com.campus_connect.user_management.exception.InvalidCredentialsException;
import com.campus_connect.user_management.exception.UnauthorizedAccessException;
import com.campus_connect.user_management.exception.UserNotFoundException;

import org.modelmapper.ModelMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;

    private final OTPService otpService;
    private final SmsService smsService;
    private final EmailService emailService;

    public StudentService( StudentRepository studentRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, JWTService jwtService, AuthenticationManager authManager, OTPService otpService, SmsService smsService, EmailService emailService) {

        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authManager = authManager;

        this.otpService = otpService;
        this.smsService = smsService;
        this.emailService = emailService;
    }

    public StudentDto saveStudent(StudentDto studentDto,String token) {
        if (!jwtService.isAdmin(token)){
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }
        studentDto.setPassword(encoder.encode(studentDto.getPassword()));
        studentDto.setRole(studentDto.getRole().toUpperCase().trim());
        studentDto.setEmail(studentDto.getEmail().trim());

        if (!studentDto.getEmail().toLowerCase().contains("@gmail.com")&&!studentDto.getEmail().toLowerCase().contains("@campusconnect.com")) {
            throw new IllegalArgumentException("Email must be a valid Gmail address ending with '@gmail.com' or '@campusconnect.com'.");
        }

        Student student = modelMapper.map(studentDto, Student.class);
        Student savedStudent = studentRepository.save(student);
        studentDto.setId(savedStudent.getId());

        return modelMapper.map(savedStudent, StudentDto.class);
    }

    public List<StudentDto> getAllStudents() {


        return mapToStudentDtoList(studentRepository.findAll());
    }

    public StudentDto getStudentByEmail(String email) {

        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for this Email: " + email));

        return  mapToStudentDto(student);
    }

    public StudentDto updateStudent(StudentDto studentDto, Long enrollmentNo) {
        Student student = studentRepository.findByEnrollmentNumber(enrollmentNo)
                .orElseThrow(() -> new UserNotFoundException("User not found for this Enrollment Number: " + enrollmentNo));

        student.setEmail(studentDto.getEmail());
        student.setPassword(encoder.encode(studentDto.getPassword()));
        student.setCourse(studentDto.getCourse());
        student.setSemester(studentDto.getSemester());
        student.setDivision(studentDto.getDivision());
        student.setGradStartYear(studentDto.getGradStartYear());
        student.setGradEndYear(studentDto.getGradEndYear());
        student.setEnrollmentNumber(studentDto.getEnrollmentNumber());
        student.setImageUrl(studentDto.getImageUrl());

        Student updatedStudent = studentRepository.save(student);

        return modelMapper.map(updatedStudent, StudentDto.class);
    }

    public String setImageUrl(Long enrollmentNo,String imageUrl) {
        Student student = studentRepository.findByEnrollmentNumber(enrollmentNo)
                .orElseThrow(() -> new UserNotFoundException("User not found for this Enrollment Number: " + enrollmentNo));


        student.setImageUrl(imageUrl.trim());
        studentRepository.save(student);

        return "image is set";

    }

    public StudentDto verify(StudentDto studentDto) {
        Student student = studentRepository.findByEmail(studentDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found for this Email: " + studentDto.getEmail()));

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(studentDto.getEmail(), studentDto.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateTokenForStudent(studentDto);
                StudentDto studentResponseDto = mapToStudentDto(student);
                studentResponseDto.setBearerToken(token);
                return studentResponseDto;
            }
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        throw new RuntimeException("Unexpected error during authentication");
    }
    public StudentDto sendOTP(StudentDto studentDto) {
        Student student = studentRepository.findByEmail(studentDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found for this Email: " + studentDto.getEmail()));

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(studentDto.getEmail(), studentDto.getPassword())
            );

            if (authentication.isAuthenticated()) {
              //  String otp = otpService.generateOTP();
                String otp = "123456";

                try {
                 /*   if (studentDto.getEmail().trim().toLowerCase().contains("@")) {
                        emailService.sendOTP(studentDto.getEmail().trim().toLowerCase(), otp);
                    } else {
                        smsService.sendOTP(studentDto.getEmail().trim().toLowerCase(), otp);
                    }*/
                    OTPStorage.storeOTP(studentDto.getEmail().trim().toLowerCase(), otp);

                } catch (Exception e) {
                    throw new InvalidCredentialsException("Invalid email or password");
                }
                StudentDto studentResponseDto = mapToStudentDto(student);
                studentResponseDto.setLastOTP(Long.valueOf(otp));
                return studentResponseDto;
            }
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        throw new RuntimeException("Unexpected error during authentication");
    }


    private StudentDto mapToStudentDto(Student student) {
        return modelMapper.map(student, StudentDto.class);
    }

    private List<StudentDto> mapToStudentDtoList(List<Student> students) {
        return students.stream()
                .map(this::mapToStudentDto) // Use the single-entity mapping method
                .toList();
    }






}

