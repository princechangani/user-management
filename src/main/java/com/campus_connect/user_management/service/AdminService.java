package com.campus_connect.user_management.service;

import com.campus_connect.user_management.DTO.AdminDto;
import com.campus_connect.user_management.DataEntity.Admin;
import com.campus_connect.user_management.Repository.AdminRepository;
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

import java.util.List;

@Service
public class AdminService {


    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;


    public AdminService(AdminRepository adminRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, JWTService jwtService, AuthenticationManager authManager) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public List<AdminDto> getAllAdmins(AdminDto userDto) {
        Admin userEntity = adminRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found for this Email: " + userDto.getEmail()));
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword())
            );

            if (authentication.isAuthenticated()) {

                List<Admin> user = adminRepository.findAll();
                return mapToAdminDtoList(user);
            }
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        throw new RuntimeException("Unexpected error during authentication");


    }
public List<AdminDto> getAllAdmins() {

        List<Admin> user = adminRepository.findAll();
        return mapToAdminDtoList(user);

}

    public AdminDto saveAdmin(AdminDto adminDto,String token) {
        if (!jwtService.isAdmin(token)){
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }
        adminDto.setPassword(encoder.encode(adminDto.getPassword()));
        adminDto.setRole(adminDto.getRole().toUpperCase().trim());
        extracted(adminDto);
        if (!adminDto.getEmail().toLowerCase().contains("@gmail.com")&&!adminDto.getEmail().toLowerCase().contains("@campusconnect.com")) {
            throw new IllegalArgumentException("Email must be a valid Gmail address ending with '@gmail.com' or '@campusconnect.com'.");
        }

        Admin admin = modelMapper.map(adminDto, Admin.class);
        Admin savedAdmin = adminRepository.save(admin);
        System.out.println("Generated Admin ID: " + savedAdmin.getId());
        adminDto.setId(savedAdmin.getId());
        return modelMapper.map(savedAdmin, AdminDto.class);
    }

    private static void extracted(AdminDto adminDto) {
        adminDto.setEmail(adminDto.getEmail().trim());
    }


    public AdminDto verify(AdminDto user) {
        Admin admin = adminRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found for this Email: " + user.getEmail()));

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(user);
                AdminDto adminDto = mapToAdminDto(admin);
                adminDto.setBearerToken(token);
                return adminDto;
            }
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        throw new RuntimeException("Unexpected error during authentication");
    }


    private AdminDto mapToAdminDto(Admin userEntity) {
        AdminDto userDto = modelMapper.map(userEntity, AdminDto.class);
        return userDto;
    }

    private List<AdminDto> mapToAdminDtoList(List<Admin> userEntities) {
        return userEntities.stream()
                .map(this::mapToAdminDto) // Use the single-entity mapping method
                .toList();
    }







}
