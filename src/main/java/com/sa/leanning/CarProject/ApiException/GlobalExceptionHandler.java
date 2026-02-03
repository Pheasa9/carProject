package com.sa.leanning.CarProject.ApiException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 🔥 Handle custom API Exception
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleApiException(ApiException ex) {
        ErrorResponse error = new ErrorResponse(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    // 🔥 Handle Validation Errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Invalid input");

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.badRequest().body(error);
    }

    // 🔥 Handle Unique Constraint / Duplicate Key
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDuplicateException(DataIntegrityViolationException ex) {

        String message = "Duplicate value. This data already exists.";

        // OPTIONAL: auto-detect model-color duplicate
        if (ex.getMessage().contains("model_id") && ex.getMessage().contains("color_id")) {
            message = "This model with this color already exists.";
        }

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 🔥 Catch all exceptions (last fallback)
   
}
