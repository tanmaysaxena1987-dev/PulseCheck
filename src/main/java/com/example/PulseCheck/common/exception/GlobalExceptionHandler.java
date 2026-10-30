package com.example.PulseCheck.common.exception;

import com.example.PulseCheck.user.exception.UserWithEmailAlreadyExists;
import com.example.PulseCheck.user.dto.ExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(UserWithEmailAlreadyExists.class)
    public ResponseEntity<ExceptionDto> userWithEmailAlreadyExists(Exception ex, HttpServletRequest request){
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(),"UserWithEmailAlreadyExists",409,request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionDto);
    }
}
