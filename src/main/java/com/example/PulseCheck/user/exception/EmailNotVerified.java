package com.example.PulseCheck.user.exception;

public class EmailNotVerified extends RuntimeException {
    public EmailNotVerified(String message) {
        super(message);
    }
}
