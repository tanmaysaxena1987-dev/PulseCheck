package com.example.PulseCheck.monitor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WebsiteMonitorRequestDto {
    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 2048)
    private String url;


    @NotNull
    @Positive
    private Integer intervalInSecond;

    @NotNull
    @Positive
    private Integer timeoutSecond;
}
