package com.example.PulseCheck.user.exception;

public class UserIsAlreadyVerified extends RuntimeException {
    public UserIsAlreadyVerified(String message) {
        super(message);
    }
}
