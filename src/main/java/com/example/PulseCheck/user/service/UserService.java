package com.example.PulseCheck.user.service;

import com.example.PulseCheck.user.entity.User;
import com.example.PulseCheck.user.repository.UserRepo;
import com.example.PulseCheck.user.exception.UserWithEmailAlreadyExists;
import com.example.PulseCheck.user.dto.UserRegisterRequestDto;
import com.example.PulseCheck.user.dto.UserRegisterResponseDto;
import com.example.PulseCheck.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
    public UserRegisterResponseDto register(UserRegisterRequestDto userRegisterRequestDto) {
        if(userRepo.existsByEmail(userRegisterRequestDto.getEmail()))
            throw new UserWithEmailAlreadyExists("User with email already exists");
        User user=userMapper.maptoUser(userRegisterRequestDto);
        userRepo.save(user);
        return userMapper.maptoUserRegisterResponseDto(user);
    }
}
