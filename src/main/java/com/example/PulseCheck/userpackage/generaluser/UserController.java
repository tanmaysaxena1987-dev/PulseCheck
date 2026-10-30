package com.example.PulseCheck.userpackage.generaluser;

import com.example.PulseCheck.userpackage.generaluser.userdto.UserRegisterRequestDto;
import com.example.PulseCheck.userpackage.generaluser.userdto.UserRegisterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
