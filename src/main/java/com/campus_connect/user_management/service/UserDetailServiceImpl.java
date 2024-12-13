package com.campus_connect.user_management.service;

import com.campus_connect.user_management.DataEntity.Admin;
import com.campus_connect.user_management.DataEntity.Faculty;
import com.campus_connect.user_management.DataEntity.Student;
import com.campus_connect.user_management.Repository.AdminRepository;
import com.campus_connect.user_management.Repository.FacultyRepository;
import com.campus_connect.user_management.Repository.StudentRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final StudentRepository studentRepo;
    private final FacultyRepository facultyRepo;
    private final AdminRepository adminRepo;

    public UserDetailServiceImpl(StudentRepository studentRepo, FacultyRepository facultyRepo, AdminRepository adminRepo) {
        this.studentRepo = studentRepo;
        this.facultyRepo = facultyRepo;
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Admin admin = adminRepo.findByEmail(email).orElse(null);
        if (admin != null) {
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles(admin.getRole())
                    .build();
        }

        Faculty faculty = facultyRepo.findByEmail(email).orElse(null);
        if (faculty != null) {
            return User.builder()
                    .username(faculty.getEmail())
                    .password(faculty.getPassword())
                    .roles(faculty.getRole())
                    .build();
        }

        Student student = studentRepo.findByEmail(email).orElse(null);
        if (student != null) {
            return User.builder()
                    .username(student.getEmail())
                    .password(student.getPassword())
                    .roles(student.getRole())
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
