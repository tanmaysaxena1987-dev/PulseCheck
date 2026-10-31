package com.example.PulseCheck.user.controller;

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
    @GetMapping("/login")
    @Operation(summary = "Login As a User")
    public ResponseEntity<String> authenticate(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.authenticate(loginDto.getEmail(), loginDto.getPassword()));
    }
}
