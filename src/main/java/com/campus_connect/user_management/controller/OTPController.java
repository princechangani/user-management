package com.campus_connect.user_management.controller;

import com.campus_connect.user_management.OTP.EmailService;
import com.campus_connect.user_management.OTP.OTPService;
import com.campus_connect.user_management.OTP.OTPStorage;
import com.campus_connect.user_management.OTP.SmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class OTPController {

    private final OTPService otpService;
    private final SmsService smsService;
    private final EmailService emailService;

    public OTPController(OTPService otpService, SmsService smsService, EmailService emailService) {
        this.otpService = otpService;
        this.smsService = smsService;
        this.emailService = emailService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOTP(@RequestParam String contact) {
        String otp = otpService.generateOTP();
        try {
            if (contact.contains("@")) {
                // Email
                emailService.sendOTP(contact, otp);
            } else {
                // SMS
                smsService.sendOTP(contact, otp);
            }
            OTPStorage.storeOTP(contact, otp);
            return ResponseEntity.ok("OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending OTP");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestParam String contact, @RequestParam String otp) {
        if (OTPStorage.validateOTP(contact, otp)) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired OTP");
        }
    }
}
