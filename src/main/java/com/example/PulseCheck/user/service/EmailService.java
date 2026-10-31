package com.example.PulseCheck.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {
        System.out.println("mail user=[" + System.getenv("MAIL_USERNAME") + "], passLen=" +
                (System.getenv("MAIL_PASSWORD") == null ? "null" : System.getenv("MAIL_PASSWORD").length()));
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Pulse Check- Email Verification");
        message.setText("Your OTP is: " + otp+"it's valid for 5 minutes.");
        mailSender.send(message);
    }
}
