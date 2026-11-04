package com.example.PulseCheck.monitor.service;

import com.example.PulseCheck.monitor.dto.WebsiteMonitorRequestDto;
import com.example.PulseCheck.monitor.mapper.MonitorMapper;
import com.example.PulseCheck.monitor.model.Website;
import com.example.PulseCheck.monitor.repo.MonitorRepo;
import com.example.PulseCheck.user.entity.User;
import com.example.PulseCheck.user.repository.UserRepo;
import com.example.PulseCheck.user.exception.EmailNotVerified;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitorService {
    private final UserRepo userRepo;
    private final MonitorMapper monitorMapper;
    private final MonitorRepo monitorRepo;
    public Void addWebsite(WebsiteMonitorRequestDto  websiteMonitorRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepo.findByEmail(authentication.getName());
        if (!user.isEmailVerified()) {
            throw new EmailNotVerified("Email must be verified before adding a website monitor");
        }
        Website website=monitorMapper.mapToWebsite(websiteMonitorRequestDto);
        website.setUser(user);
        monitorRepo.save(website);
        return null;
    }
}
