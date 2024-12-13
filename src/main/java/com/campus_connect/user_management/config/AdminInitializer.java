package com.campus_connect.user_management.config;

import com.campus_connect.user_management.DataEntity.Admin;
import com.campus_connect.user_management.Repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.count() == 0) { // Check if admin exists
            Admin admin = new Admin();
            admin.setEmail("admin@campusconnect.com");
            admin.setPassword(passwordEncoder.encode("admin")); // Set a secure password
            admin.setRole("ADMIN");
            adminRepository.save(admin);
            System.out.println("Default admin created with email: admin@campusconnect.com and password: admin");
        }else {
            System.out.println("Admin already exists. Skipping initialization.");
        }
    }
}
