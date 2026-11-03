package com.example.PulseCheck.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequestDto {
    @NotBlank
    @Size(max=100)
    private String name;
    @NotBlank
    @Email
    @Size(max=100)
    private String email;
    @NotBlank
    @Size(min=8,max=72)
    private String password;
}
