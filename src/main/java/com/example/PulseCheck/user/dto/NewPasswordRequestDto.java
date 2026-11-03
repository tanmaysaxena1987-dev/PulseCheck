package com.example.PulseCheck.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPasswordRequestDto {
    @Size(min = 8, max = 72)
    private String password;
}
