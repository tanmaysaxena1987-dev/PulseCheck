package com.example.PulseCheck.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerificationDto {
    @NotBlank
    @Size(min = 8, max = 8)
    @Pattern(regexp = "\\d{8}")
    private String otp;
}
