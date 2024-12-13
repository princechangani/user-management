package com.campus_connect.user_management.service;

import com.campus_connect.user_management.DTO.StudentDto;
import com.campus_connect.user_management.DataEntity.Student;
import com.campus_connect.user_management.Repository.StudentRepository;

import com.campus_connect.user_management.StudentClientRepository.ResultClient;
import com.campus_connect.user_management.StudentClientRepository.StudentClient;
import com.campus_connect.user_management.exception.InvalidCredentialsException;
import com.campus_connect.user_management.exception.UnauthorizedAccessException;
import com.campus_connect.user_management.exception.UserNotFoundException;
import com.campus_connect.user_management.responce.ResultDto;
import com.campus_connect.user_management.responce.StudentResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final ResultClient resultClient;
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final StudentClient studentClient;

    public StudentService(ResultClient resultClient, StudentRepository studentRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, JWTService jwtService, AuthenticationManager authManager, StudentClient studentClient) {
        this.resultClient = resultClient;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.studentClient = studentClient;
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
    public StudentDto getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found for this Email: " + email));
        return mapToStudentDto(student);
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

