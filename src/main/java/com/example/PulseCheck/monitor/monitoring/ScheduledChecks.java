package com.example.PulseCheck.monitor.monitoring;

import com.example.PulseCheck.monitor.model.Website;
import com.example.PulseCheck.monitor.enums.CurrentStatus;
import com.example.PulseCheck.monitor.repo.MonitorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
@RequiredArgsConstructor
public class ScheduledChecks {
    private final MonitorRepo monitorRepo;
    private final RestClient restClient;
    private final ExecutorService executorService= Executors.newFixedThreadPool(10);
    private final StringRedisTemplate stringRedisTemplate;
    @Scheduled(cron="0 */1 * * * *")
    public void Check(){
        for(Website website : monitorRepo.findAll()){
           executorService.submit(()->checkWebsite(website));
        }
    }
    public void checkWebsite(Website website){
        String key="monitor:check:"+website.getId();
        if(Boolean.TRUE.equals(stringRedisTemplate.hasKey(key)))
            return;

        String result;
        CurrentStatus currentStatus;
        try{
            result=restClient.get()
                    .uri(website.getUrl())
                    .exchange((request,response)->{
                        int statusCode=response.getStatusCode().value();
                        return "HTTP "+statusCode;
                    });
            currentStatus=result.matches("HTTP 2\\d\\d") ? CurrentStatus.UP : CurrentStatus.DOWN;
        }
        catch (Exception e){
            result="ERROR "+e.getClass().getSimpleName();
            currentStatus=CurrentStatus.DOWN;
        }
        stringRedisTemplate.opsForValue().set(key,result,Duration.ofSeconds(website.getIntervalInSecond()));

        LocalDateTime checkedAt=LocalDateTime.now();
        website.setCurrentState(currentStatus);
        website.setLastCheckedAt(checkedAt);
        website.setNextCheckAt(checkedAt.plusSeconds(website.getIntervalInSecond()));
        monitorRepo.save(website);
    }
}
