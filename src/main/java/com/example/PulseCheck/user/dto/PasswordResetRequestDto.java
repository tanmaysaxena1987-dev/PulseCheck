package com.example.PulseCheck.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequestDto {
    @Email
    @NotBlank
    private String email;
    private String otp;
}
