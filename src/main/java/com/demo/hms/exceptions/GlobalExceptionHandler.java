package com.demo.hms.exceptions;

import com.demo.hms.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class, BadCredentialsException.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        if(ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }
        if(ex instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<>(ErrorResponse.builder().message(ex.getMessage()).build(), status);
    }
}
