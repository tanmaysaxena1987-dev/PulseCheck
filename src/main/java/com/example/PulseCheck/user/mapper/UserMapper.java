package com.example.PulseCheck.user.mapper;

import com.example.PulseCheck.user.enums.Role;
import com.example.PulseCheck.user.enums.Status;
import com.example.PulseCheck.user.entity.User;
import com.example.PulseCheck.user.dto.UserRegisterRequestDto;
import com.example.PulseCheck.user.dto.UserRegisterResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder encoder;
    public User maptoUser(UserRegisterRequestDto userRegisterRequestDto) {
        User  user=new User();
        user.setEmail(userRegisterRequestDto.getEmail());
        user.setPassword(encoder.encode(userRegisterRequestDto.getPassword()));
        user.setName(userRegisterRequestDto.getName());
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        return user;
    }
    public UserRegisterResponseDto maptoUserRegisterResponseDto(User user) {
        UserRegisterResponseDto userRegisterResponseDto=new UserRegisterResponseDto();
        userRegisterResponseDto.setEmail(user.getEmail());
        userRegisterResponseDto.setName(user.getName());
        userRegisterResponseDto.setRole(user.getRole());
        userRegisterResponseDto.setId(user.getId());
        return userRegisterResponseDto;
    }
}
