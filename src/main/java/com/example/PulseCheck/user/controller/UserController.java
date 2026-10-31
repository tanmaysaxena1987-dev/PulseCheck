package com.example.PulseCheck.user.controller;

import com.example.PulseCheck.user.dto.UserViewResponseDto;
import com.example.PulseCheck.user.service.UserService;
import com.example.PulseCheck.user.dto.UserRegisterRequestDto;
import com.example.PulseCheck.user.dto.UserRegisterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User Related Operations")
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
}
