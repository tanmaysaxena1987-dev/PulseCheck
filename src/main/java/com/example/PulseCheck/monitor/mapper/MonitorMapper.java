package com.example.PulseCheck.monitor.mapper;

import com.example.PulseCheck.monitor.dto.WebsiteMonitorRequestDto;
import com.example.PulseCheck.monitor.model.Website;
import org.springframework.stereotype.Component;

import javax.management.monitor.Monitor;

@Component
public class MonitorMapper {
    public Website mapToWebsite(WebsiteMonitorRequestDto websiteMonitorRequestDto) {
        String type=websiteMonitorRequestDto.getUrl().substring(0,5);
        System.out.println("type:"+type);
        if(!type.equals("Https"))
            type="Https";
        Website website=new Website();
        website.setName(websiteMonitorRequestDto.getName());
        website.setUrl(websiteMonitorRequestDto.getUrl());
        website.setIntervalInSecond(websiteMonitorRequestDto.getIntervalInSecond());
        website.setTimeoutSecond(websiteMonitorRequestDto.getTimeoutSecond());
        website.setType(type);
        return website;
    }
}
