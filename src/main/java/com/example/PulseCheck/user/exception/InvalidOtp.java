package com.example.PulseCheck.user.exception;

public class InvalidOtp extends RuntimeException {
    public InvalidOtp(String message) {
        super(message);
    }
}
