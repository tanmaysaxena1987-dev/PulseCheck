package com.example.PulseCheck.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @Email
    private String email;
    @Size(min = 3,max=72)
    private String password;
}
