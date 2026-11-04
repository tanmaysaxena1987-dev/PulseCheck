package com.example.PulseCheck.monitor.mapper;

import com.example.PulseCheck.monitor.dto.WebsiteMonitorRequestDto;
import com.example.PulseCheck.monitor.model.Website;
import org.springframework.stereotype.Component;

@Component
public class MonitorMapper {
    public Website mapToWebsite(WebsiteMonitorRequestDto websiteMonitorRequestDto) {
        Website website=new Website();
        website.setName(websiteMonitorRequestDto.getName());
        website.setUrl(websiteMonitorRequestDto.getUrl());
        website.setIntervalInSecond(websiteMonitorRequestDto.getIntervalInSecond());
        website.setTimeoutSecond(websiteMonitorRequestDto.getTimeoutSecond());
        return website;
    }
}
