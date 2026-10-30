package com.example.PulseCheck.userpackage.generaluser.userdto;

import com.example.PulseCheck.userpackage.common.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDto {
    private String name;
    private String email;
    private String password;
}
