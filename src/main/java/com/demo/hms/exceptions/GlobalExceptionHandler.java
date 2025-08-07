package com.demo.hms.exceptions;

import com.demo.hms.dto.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {
            ResourceNotFoundException.class,
            BadCredentialsException.class,
            ExpiredJwtException.class,
            RuntimeException.class
    })
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        if(ex instanceof ResourceNotFoundException) {
            statusCode = HttpStatus.NOT_FOUND;
        }
        if(ex instanceof BadCredentialsException) {
            statusCode = HttpStatus.UNAUTHORIZED;
        }
        if(ex instanceof  ExpiredJwtException) {
            statusCode = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(ErrorResponse.builder().message(ex.getMessage()).build(), statusCode);
    }
}
