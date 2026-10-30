package com.example.PulseCheck.common;

import com.example.PulseCheck.userpackage.common.UserWithEmailAlreadyExists;
import com.example.PulseCheck.userpackage.dtos.ExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(UserWithEmailAlreadyExists.class)
    public ExceptionDto userWithEmailAlreadyExists(Exception ex, HttpServletRequest request){
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(),"UserWithEmailAlreadyExists",409,request.getRequestURI());
        return exceptionDto;
    }
}
