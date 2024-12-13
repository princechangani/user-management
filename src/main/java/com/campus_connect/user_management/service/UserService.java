package com.campus_connect.user_management.service;

import com.campus_connect.user_management.DTO.AdminDto;
import com.campus_connect.user_management.DTO.StudentDto;
import com.campus_connect.user_management.DataEntity.Student;
import com.campus_connect.user_management.Repository.AdminRepository;
import com.campus_connect.user_management.Repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ModelMapper modelMapper ;
    private final StudentRepository studentRepository;
  private final AdminRepository adminRepository;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder;




    public UserService(ModelMapper modelMapper, StudentRepository studentRepository, AdminRepository adminRepository, AuthenticationManager authManager, JWTService jwtService, BCryptPasswordEncoder encoder) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }





    public StudentDto saveStudent(StudentDto studentDto) {
        Student student = modelMapper.map(studentDto, Student.class);
        student.setPassword(encoder.encode(student.getPassword()));
        Student savedStudent = studentRepository.save(student);
        return modelMapper.map(savedStudent, StudentDto.class);
    }



    private AdminDto mapToUserDto(AdminDto userEntity) {
        AdminDto userDto = modelMapper.map(userEntity, AdminDto.class);
        return userDto;
    }

}
