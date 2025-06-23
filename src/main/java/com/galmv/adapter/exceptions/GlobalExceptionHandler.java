package com.galmv.adapter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.galmv.domain.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException exception) {
      Map<String, String> errors = new HashMap<>();

      errors.put("resource", exception.getMessage());

      return new ResponseEntity<Object>(
        new ExceptionResponse(
            "RESOURCE_NOT_FOUND",
            LocalDateTime.now().toString(),
            HttpStatus.NOT_FOUND.value(),
            exception.getClass().getName(),
            errors
        ), HttpStatus.NOT_FOUND
      );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception exception) {
      Map<String, String> errors = new HashMap<>();

      errors.put("resource", exception.getMessage());

      return new ResponseEntity<Object>(
        new ExceptionResponse(
            "INTERNAL_SERVER_ERROR",
            LocalDateTime.now().toString(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            exception.getClass().getName(),
            errors
        ), HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
}
