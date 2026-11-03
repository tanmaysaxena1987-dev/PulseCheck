package com.example.PulseCheck.common.exception;

import com.example.PulseCheck.user.exception.InvalidOtp;
import com.example.PulseCheck.user.exception.MaximumAttemptLimitReached;
import com.example.PulseCheck.user.exception.UserAlreadyExists;
import com.example.PulseCheck.user.dto.ExceptionDto;
import com.example.PulseCheck.user.exception.UserIsAlreadyVerified;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
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
}
