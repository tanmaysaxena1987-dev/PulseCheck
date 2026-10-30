package com.example.PulseCheck.user.dto;

import com.example.PulseCheck.user.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserRegisterResponseDto {
    private String name;
    private String email;
    private Role role;
    private UUID id;
}
