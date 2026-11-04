package com.example.PulseCheck.common.exception;

import com.example.PulseCheck.user.exception.*;
import com.example.PulseCheck.user.dto.ExceptionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
    @ExceptionHandler(EmailNotVerified.class)
    public ResponseEntity<ExceptionDto> emailNotVerified(Exception ex, HttpServletRequest request){
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), "EmailNotVerified", 403, request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDto);
    }

    @ExceptionHandler(UserAlreadyExists.class)
    public ResponseEntity<ExceptionDto> userWithEmailAlreadyExists(Exception ex, HttpServletRequest request){
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(),"UserWithEmailAlreadyExists",409,request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionDto);
    }
    @ExceptionHandler(UserIsAlreadyVerified.class)
    public ResponseEntity<ExceptionDto> userIsAlreadyVerified(Exception ex,HttpServletRequest request){
        ExceptionDto exceptionDto=new ExceptionDto(ex.getMessage(),"UserIsAlreadyVerified",409,request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionDto);
    }
    @ExceptionHandler(MaximumAttemptLimitReached.class)
    public ResponseEntity<ExceptionDto> MaximumAttemptLimitReached(Exception ex,HttpServletRequest request){
        ExceptionDto exceptionDto=new ExceptionDto(ex.getMessage(),"TooManyRequest",429,request.getRequestURI());
        return  ResponseEntity
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .body(exceptionDto);
    }
    @ExceptionHandler(InvalidOtp.class)
    public ResponseEntity<ExceptionDto> InvalidOtp(Exception ex,HttpServletRequest request){
        ExceptionDto exceptionDto=new ExceptionDto(ex.getMessage(),"InvalidOtp",400,request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionDto);
    }
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ExceptionDto> UserNotFound(Exception ex,HttpServletRequest request){
        ExceptionDto exceptionDto=new ExceptionDto(ex.getMessage(),"UserNotFound",404,request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionDto);
    }
}
