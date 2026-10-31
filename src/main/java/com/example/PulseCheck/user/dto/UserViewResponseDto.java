package com.example.PulseCheck.user.dto;

import com.example.PulseCheck.user.enums.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Setter
public class UserViewResponseDto {
    private UUID Id;
    private String email;
    private String name;
    private Role role;
}
