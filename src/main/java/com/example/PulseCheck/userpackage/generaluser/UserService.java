package com.example.PulseCheck.userpackage.generaluser;

import com.example.PulseCheck.userpackage.common.User;
import com.example.PulseCheck.userpackage.common.UserRepo;
import com.example.PulseCheck.userpackage.common.UserWithEmailAlreadyExists;
import com.example.PulseCheck.userpackage.generaluser.userdto.UserRegisterRequestDto;
import com.example.PulseCheck.userpackage.generaluser.userdto.UserRegisterResponseDto;
import com.example.PulseCheck.userpackage.mappers.UserMapper;
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
