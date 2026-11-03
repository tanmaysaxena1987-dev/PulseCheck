package com.example.PulseCheck.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @Email
    @NotBlank
    @Size(max=100)
    private String email;
    @Size(min = 3,max=72)
    @NotBlank
    private String password;
}
