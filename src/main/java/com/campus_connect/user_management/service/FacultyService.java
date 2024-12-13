package com.campus_connect.user_management.service;

import com.campus_connect.user_management.DTO.FacultyDto;
import com.campus_connect.user_management.DataEntity.Faculty;
import com.campus_connect.user_management.Repository.FacultyRepository;
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
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;
    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    public FacultyService(FacultyRepository facultyRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder, JWTService jwtService, AuthenticationManager authManager) {
        this.facultyRepository = facultyRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public FacultyDto saveFaculty(FacultyDto facultyDto,String token) {
        if (!jwtService.isAdmin(token)){
            throw new UnauthorizedAccessException("Only admin can perform this action.");
        }
        facultyDto.setPassword(encoder.encode(facultyDto.getPassword()));
        facultyDto.setRole(facultyDto.getRole().toUpperCase().trim());
        facultyDto.setEmail(facultyDto.getEmail().trim());
        if (!facultyDto.getEmail().toLowerCase().contains("@gmail.com")&&!facultyDto.getEmail().toLowerCase().contains("@campusconnect.com")) {
            throw new IllegalArgumentException("Email must be a valid Gmail address ending with '@gmail.com' or '@campusconnect.com'.");
        }

        Faculty faculty = modelMapper.map(facultyDto, Faculty.class);
        Faculty savedFaculty = facultyRepository.save(faculty);
        facultyDto.setId(savedFaculty.getId());

        return modelMapper.map(savedFaculty, FacultyDto.class);
    }
public List<FacultyDto> getAllFaculties() {
        return mapToFacultyDtoList(facultyRepository.findAll());

    }
    public FacultyDto verify(FacultyDto user) {
        Faculty admin = facultyRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found for this Email: " + user.getEmail()));

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateTokenForFaculty(user);
                FacultyDto adminDto = mapToFacultyDto(admin);
                adminDto.setBearerToken(token);
                return adminDto;
            }
        } catch (AuthenticationException e) {
            System.err.println("Authentication failed: " + e.getMessage());
            throw new InvalidCredentialsException("Invalid email or password");
        }

        throw new RuntimeException("Unexpected error during authentication");
    }

    private FacultyDto mapToFacultyDto(Faculty faculty) {
        FacultyDto adminDto = modelMapper.map(faculty, FacultyDto.class);
        return adminDto;
    }

    private List<FacultyDto> mapToFacultyDtoList(List<Faculty> faculties) {
        return faculties.stream()
                .map(this::mapToFacultyDto) // Use the single-entity mapping method
                .toList();
    }
}
