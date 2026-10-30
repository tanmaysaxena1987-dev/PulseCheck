package com.example.PulseCheck.userpackage.common;

public class UserWithEmailAlreadyExists extends RuntimeException {
    public UserWithEmailAlreadyExists(String message) {
        super(message);
    }
}
