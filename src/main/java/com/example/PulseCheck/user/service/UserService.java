package com.example.PulseCheck.user.service;

import com.example.PulseCheck.user.dto.*;
import com.example.PulseCheck.user.entity.User;
import com.example.PulseCheck.user.enums.Status;
import com.example.PulseCheck.user.exception.InvalidOtp;
import com.example.PulseCheck.user.exception.UserNotFound;
import com.example.PulseCheck.user.repository.UserRepo;
import com.example.PulseCheck.user.exception.UserAlreadyExists;
import com.example.PulseCheck.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public UserRegisterResponseDto register(UserRegisterRequestDto userRegisterRequestDto) {
        if(userRepo.existsByEmail(userRegisterRequestDto.getEmail()))
            throw new UserAlreadyExists("User with email already exists");
        User user=userMapper.maptoUser(userRegisterRequestDto);
        userRepo.save(user);
        return userMapper.maptoUserRegisterResponseDto(user);
    }

    public UserViewResponseDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(authentication.getName());
        UserViewResponseDto userViewResponseDto = userMapper.maptoUserViewResponseDto(user);
        return userViewResponseDto;
    }
    @Transactional
    public void passwordReset(String email) {
        if(userRepo.existsByEmail(email)){
            String otp=otpService.generateOtpCode();
            emailService.sendOtp(email,otp);
            otpService.saveOtpCode(email,otp);
        }
        else
            throw new UserNotFound("User not found");
    }

    public String verifyIdentity(PasswordResetRequestDto passwordResetRequestDto) {
        log.info("Email: "+passwordResetRequestDto.getEmail());
        if(!otpService.verifyOtpCode(passwordResetRequestDto.getEmail(), passwordResetRequestDto.getOtp())){
            throw new InvalidOtp("The otp you entered is incorrect");
        }
        return jwtService.generateToken(myUserDetailsService.loadUserByUsername(passwordResetRequestDto.getEmail()));
    }
    @Transactional
    public void newPassword(@Valid NewPasswordRequestDto newPasswordRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(authentication.getName());
        user.setPassword(passwordEncoder.encode(newPasswordRequestDto.getPassword()));
    }

    public void deleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userRepo.findByEmail(authentication.getName());
        userRepo.delete(user);
    }
}
