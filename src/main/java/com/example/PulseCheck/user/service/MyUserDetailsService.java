package com.example.PulseCheck.user.service;

import com.example.PulseCheck.user.repository.UserRepo;
import com.example.PulseCheck.user.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.PulseCheck.user.entity.User user=userRepo.findByEmail(username);
        if(user!=null){
            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(String.valueOf(user.getRole()))
                    .disabled(user.getStatus() == Status.SUSPENDED)
                    .build();
        }
        throw new UsernameNotFoundException(username);
    }
}
