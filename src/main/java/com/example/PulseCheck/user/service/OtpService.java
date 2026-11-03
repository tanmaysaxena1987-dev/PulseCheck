package com.example.PulseCheck.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private final SecureRandom secureRandom=new SecureRandom();
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;
    public String generateOtpCode() {
        return String.format("%08d", secureRandom.nextInt(100_000_000));
    }
    public Void saveOtpCode(String email,String otpCode) {
        String key="otp"+email;
        String hashedOtp=passwordEncoder.encode(otpCode);
        redisTemplate.opsForValue().set(key, hashedOtp, Duration.ofMinutes(5));
        return null;
    }
    public boolean verifyOtpCode(String email, String otpCode) {
        String key = "otp" + email;
        String storedHash = redisTemplate.opsForValue().get(key);
        log.info("key={}, found={}", key, storedHash != null);
        if (storedHash == null) {
            return false; // expired, never saved, or already used
        }
        boolean matches = passwordEncoder.matches(otpCode, storedHash);
        if (matches) {
            redisTemplate.delete(key);
        }
        return matches;
    }
}
