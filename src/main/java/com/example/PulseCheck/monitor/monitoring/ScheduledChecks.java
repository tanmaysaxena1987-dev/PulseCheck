package com.example.PulseCheck.monitor.monitoring;

import com.example.PulseCheck.monitor.model.Website;
import com.example.PulseCheck.monitor.enums.CurrentStatus;
import com.example.PulseCheck.monitor.repo.MonitorRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.StringRedisTemplate;
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
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end",
            Long.class
    );

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

        String lockKey="monitor:lock:"+website.getId();
        String lockToken=UUID.randomUUID().toString();
        long lockSeconds=Math.max(60L,website.getTimeoutSecond()+30L);
        Boolean acquired=stringRedisTemplate.opsForValue().setIfAbsent(
                lockKey,lockToken,Duration.ofSeconds(lockSeconds));
        if(!Boolean.TRUE.equals(acquired))
            return;

        try {
            if(Boolean.TRUE.equals(stringRedisTemplate.hasKey(key)))
                return;

            CurrentStatus currentStatus;
            String checkResult;
            try{
                int statusCode=restClient.get()
                        .uri(website.getUrl())
                        .exchange((request,response)->response.getStatusCode().value());
                currentStatus=(statusCode>=200&&statusCode<400)?CurrentStatus.UP:CurrentStatus.DOWN;
                checkResult="HTTP "+statusCode;
            }
            catch (Exception e){
                currentStatus=CurrentStatus.DOWN;
                checkResult="ERROR "+e.getClass().getSimpleName();
                log.warn("Website check failed for {}", website.getUrl(), e);
            }
            stringRedisTemplate.opsForValue().set(key,checkResult,Duration.ofSeconds(website.getIntervalInSecond()));

            LocalDateTime checkedAt=LocalDateTime.now();
            website.setCurrentState(currentStatus);
            website.setLastCheckedAt(checkedAt);
            website.setNextCheckAt(checkedAt.plusSeconds(website.getIntervalInSecond()));
            monitorRepo.save(website);
        } finally {
            stringRedisTemplate.execute(RELEASE_LOCK_SCRIPT,List.of(lockKey),lockToken);
        }
    }
}
