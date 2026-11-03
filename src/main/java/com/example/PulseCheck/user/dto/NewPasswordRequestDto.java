package com.example.PulseCheck.user.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordRequestDto {
    @NotBlank
    @Size(min = 8, max = 72)
    private String password;
}
