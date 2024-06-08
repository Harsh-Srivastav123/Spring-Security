package com.sprinsecurity.security.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) throws Exception {
        HashMap<String,Object> error=new HashMap<>();
        if (ex instanceof AccessDeniedException || ex instanceof AuthenticationException) {
            log.info("error handle here !!");
            error.put("httpStatus", HttpStatus.BAD_REQUEST);
            error.put("message",ex.getMessage());
            throw  ex;
        }else {
            return null;
        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body((error);
        // Handle other exceptions
        // ...
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> customException(BadRequestException exception) {
        log.info("error reach here !!");
        HashMap<String,Object> error=new HashMap<>();
        error.put("httpStatus", HttpStatus.BAD_REQUEST);
        error.put("message",exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error);
    }
}
