package com.example.PulseCheck.monitor.monitoring;


import com.example.PulseCheck.monitor.enums.CurrentStatus;
import com.example.PulseCheck.monitor.model.Website;
import com.example.PulseCheck.monitor.repo.MonitorRepo;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledChecks {
    private static final DefaultRedisScript<Long> RELEASE_LOCK_SCRIPT = new DefaultRedisScript<>(
            "if redis.call('get',KEYS[1]) == ARGV[1])"
            +
                    "then return redis.call('del',KEYS[1])"+
                    "else return 0 end",
            Long.class
    );
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final StringRedisTemplate stringRedisTemplate;
    private final MonitorRepo monitorRepo;
    private final RestClient restClient;
    @Scheduled(cron = "0 */1 * * * *")
    public Void checks(){
        for(Website website : monitorRepo.findAll())
            executorService.submit(()->checkWebsite(website));
        return null;
    }
    private void checkWebsite(Website website){
        String id=website.getId().toString();
        String cacheKey="monitor:check:"+id;
        String lockKey="monitor:lock:"+id;
        String lockToken= UUID.randomUUID().toString();
        long lockSeconds=Math.max(60L, website.getTimeoutSecond()+30L);
        Boolean acquired=stringRedisTemplate.opsForValue().setIfAbsent(lockKey,lockToken, Duration.ofSeconds(lockSeconds));
        if(!Boolean.TRUE.equals(acquired)){
            return;
        }
        try{
            CheckResult result=performCheck(website);
            updateWebsite(website,result);
            stringRedisTemplate.opsForValue().set(cacheKey, result.description,Duration.ofSeconds(website.getIntervalInSecond()));
        } catch (Exception e) {
            log.error("Unable to complete monitoring for {}",website.getUrl(),e);
        }
        finally {
            stringRedisTemplate.execute(RELEASE_LOCK_SCRIPT, List.of(lockKey),lockToken);
        }
    }
    public CheckResult performCheck(Website website){
        try{
            int statusCode=restClient.get()
                    .uri(website.getUrl())
                    .exchange((request,response)->response.getStatusCode().value());
            if(statusCode>=200&&statusCode<400){
                return new CheckResult(CurrentStatus.UP,"HTTP"+statusCode);
            } else if (statusCode>=500) {
                return new CheckResult(CurrentStatus.DOWN,"HTTP"+statusCode);
            }
            else {
                return new CheckResult(CurrentStatus.UNKNOWN,"HTTP"+statusCode);
            }
        }
        catch (Exception e){
            log.warn("HTTP check failed for {}:{}",website.getUrl(),e.getMessage());
                    return new CheckResult(CurrentStatus.UNKNOWN,"ERROR"+e.getClass().getSimpleName());
        }
    }
    public Void updateWebsite(Website website,CheckResult result){
        LocalDateTime checkedAt=LocalDateTime.now();
        website.setCurrentState(result.currentStatus);
        website.setLastCheckedAt(checkedAt);
        website.setNextCheckAt(checkedAt.plusSeconds(website.getIntervalInSecond()));
        monitorRepo.save(website);
        return null;
    }
    public record CheckResult(CurrentStatus currentStatus,String description){
    }
    @PreDestroy
    public void Shutdown(){
        executorService.shutdown();
    }
}