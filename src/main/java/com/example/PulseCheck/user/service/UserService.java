package com.example.PulseCheck.user.service;

import com.example.PulseCheck.user.dto.UserViewResponseDto;
import com.example.PulseCheck.user.entity.User;
import com.example.PulseCheck.user.repository.UserRepo;
import com.example.PulseCheck.user.exception.UserAlreadyExists;
import com.example.PulseCheck.user.dto.UserRegisterRequestDto;
import com.example.PulseCheck.user.dto.UserRegisterResponseDto;
import com.example.PulseCheck.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
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
}
