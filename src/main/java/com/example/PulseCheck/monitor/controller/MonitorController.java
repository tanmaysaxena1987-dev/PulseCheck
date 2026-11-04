package com.example.PulseCheck.monitor.controller;

import com.example.PulseCheck.monitor.dto.WebsiteMonitorRequestDto;
import com.example.PulseCheck.monitor.service.MonitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor")
@Tag(name = "Website Monitor Operations")
@RequiredArgsConstructor
public class MonitorController {
    private final MonitorService monitorService;
    @PostMapping("/register-website")
    @Operation(summary = "Receive a website monitor request")
    public ResponseEntity<String> receiveMonitorRequest(
            @Valid @RequestBody WebsiteMonitorRequestDto websiteMonitorRequestDto) {
        monitorService.addWebsite(websiteMonitorRequestDto);
        return ResponseEntity.
                status(HttpStatus.ACCEPTED)
                .body("Your Website Monitor Request Received Successfully");
    }
}
