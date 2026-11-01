package com.example.PulseCheck.user.exception;

public class MaximumAttemptLimitReached extends RuntimeException {
    public MaximumAttemptLimitReached(String message) {
        super(message);
    }
}
