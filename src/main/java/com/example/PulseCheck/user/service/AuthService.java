package com.example.PulseCheck.user.service;

import com.example.PulseCheck.user.entity.User;
import com.example.PulseCheck.user.exception.UserIsAlreadyVerified;
import com.example.PulseCheck.user.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final UserRepo  userRepo;
    private final EmailService emailService;
    public String authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails);
    }
    public Void sendVerificationCode() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String otp=otpService.generateOtpCode();
        otpService.saveOtpCode(email, otp);
        emailService.sendOtp(email, otp);
        return null;
    }
    @Transactional
    public boolean verifyCode(String otp) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user=userRepo.findByEmail(email);
        if(user.isEmailVerified())
            throw new UserIsAlreadyVerified("User is already verified");
        boolean verified=otpService.verifyOtpCode(email, otp);
        if(verified){
            user.setEmailVerified(true);
            userRepo.save(user);
            return true;
        }
        return false;
    }
}
