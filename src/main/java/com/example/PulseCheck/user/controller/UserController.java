package com.example.PulseCheck.user.controller;

import com.example.PulseCheck.user.dto.*;
import com.example.PulseCheck.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Related Operations")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    @Operation(summary = "Register As a User")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(userRegisterRequestDto));
    }
    @GetMapping("/getuser")
    @Operation(summary ="View User Details")
    public ResponseEntity<UserViewResponseDto> getUser(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser());
    }
    @PatchMapping("/resetpassword")
    @Operation(summary = "Reset Account Password")
    public ResponseEntity<String> passwordReset(@RequestParam @Valid String email){
        userService.passwordReset(email);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Please Check Your Inbox for instructions");
    }
    @PostMapping("/verifyidentity")
    @Operation(summary = "Verify Otp for Password Reset")
    public ResponseEntity<String> verifyIdentity(@Valid @RequestBody PasswordResetRequestDto passwordResetRequestDto){
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(userService.verifyIdentity(passwordResetRequestDto));
    }
    @PatchMapping("/newpassword")
    @Operation(summary = "Save Your new Password")
    public ResponseEntity<String> newPassword(@Valid @RequestBody NewPasswordRequestDto  newPasswordRequestDto){
        userService.newPassword(newPasswordRequestDto);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Password has been saved successfully");
    }
    @DeleteMapping("/delete-account")
    @Operation(summary = "Delete Your Account")
    public ResponseEntity<String> deleteAccount(){
        userService.deleteAccount();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Account has been deleted successfully");
    }
}
