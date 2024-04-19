package com.youssef.gamal.library_magement_system_app.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unhandled Exception = {} , Class Name of the Exception = {} ", e , e.getClass().getName());

        e.printStackTrace();
        return ResponseEntity.internalServerError()
                .build();
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> handleNoSuchFieldException(NoSuchElementException e) {
        log.error("NoSuchElementException = ", e);
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException = ", e);
        e.printStackTrace();

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorValidationResponse errorValidationResponse = ErrorValidationResponse.builder()
                .validationErrors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorValidationResponse);
    }
}
