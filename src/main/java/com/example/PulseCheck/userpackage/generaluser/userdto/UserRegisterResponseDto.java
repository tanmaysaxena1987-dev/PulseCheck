package com.example.PulseCheck.userpackage.generaluser.userdto;

import com.example.PulseCheck.userpackage.common.Role;
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
