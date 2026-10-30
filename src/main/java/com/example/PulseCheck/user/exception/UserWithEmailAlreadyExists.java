package com.example.PulseCheck.user.exception;

public class UserWithEmailAlreadyExists extends RuntimeException {
    public UserWithEmailAlreadyExists(String message) {
        super(message);
    }
}
