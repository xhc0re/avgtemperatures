package io.codenamite.avgtemperatures.controller;

import io.codenamite.avgtemperatures.exception.CorruptedCsvFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CorruptedCsvFileException.class)
    public ResponseEntity<Object> handleCorruptedCsvFileException(
            CorruptedCsvFileException ex, WebRequest request) {
        String errorMessage = ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(
            RuntimeException ex, WebRequest request) {
        String errorMessage = "Unexpected error: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

