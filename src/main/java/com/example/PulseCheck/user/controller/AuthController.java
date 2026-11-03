package com.example.PulseCheck.user.controller;

import com.example.PulseCheck.user.dto.EmailVerificationDto;
import com.example.PulseCheck.user.dto.LoginDto;
import com.example.PulseCheck.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name="Authentication Related Operation")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    @Operation(summary = "Login As a User")
    public ResponseEntity<String> authenticate(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.authenticate(loginDto.getEmail(), loginDto.getPassword()));
    }
    @PostMapping("/getotp")
    @Operation(summary = "get otp for email verification")
    public ResponseEntity<String> getOtpCode() {
        authService.sendVerificationCode();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Check Your Inbox for Otp");
    }
    @PostMapping("/verifyotp")
    @Operation(summary = "Verify otp for email verification")
    public ResponseEntity<String> verifyOtpCode(@Valid @RequestBody EmailVerificationDto emailVerificationDto) {
        String otp= emailVerificationDto.getOtp();
        boolean verified= authService.verifyCode(otp);
        if(verified) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Your Email has been verified");
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Please enter the correct OTP");
        }
    }
}
