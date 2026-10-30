package com.example.PulseCheck.userpackage.mappers;

import com.example.PulseCheck.userpackage.common.Role;
import com.example.PulseCheck.userpackage.common.Status;
import com.example.PulseCheck.userpackage.common.User;
import com.example.PulseCheck.userpackage.generaluser.userdto.UserRegisterRequestDto;
import com.example.PulseCheck.userpackage.generaluser.userdto.UserRegisterResponseDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public com.example.PulseCheck.userpackage.common.User maptoUser(UserRegisterRequestDto userRegisterRequestDto) {
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
