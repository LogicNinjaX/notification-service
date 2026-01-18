package com.nitish.notification_service.exception.handler;

import com.nitish.notification_service.dto.response.ApiResponse;
import com.nitish.notification_service.exception.custom_exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> validationExceptionHandler(MethodArgumentNotValidException ex){
        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("invalid argument", errors));
    }

    @ExceptionHandler(InvalidStatusException.class)
    public ResponseEntity<ApiResponse<Void>> invalidStatusHandler(InvalidStatusException e, HttpServletRequest request){
        return ResponseEntity.badRequest().body(
                ApiResponse.error(
                        "invalid status received",
                        Collections.singletonMap("details", e.getMessage())
                )
        );
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<ApiResponse<Void>> duplicateFieldHandler(DuplicateFieldException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error("duplicate input found", Collections.singletonMap("details", e.getMessage())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> entityNotFoundHandler(EntityNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("record not found", Collections.singletonMap("details", e.getMessage())));
    }

    @ExceptionHandler({IllegalArgumentException.class, VariableNotFoundException.class})
    public ResponseEntity<ApiResponse<Void>> illegalArgumentHandler(RuntimeException e){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponse.error("invalid input found", Collections.singletonMap("details", e.getMessage())));
    }

    @ExceptionHandler(TemplateValidationException.class)
    public ResponseEntity<ApiResponse<Void>> templateValidationHandler(TemplateValidationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error("invalid content found", Collections.singletonMap("details", e.getMessage())));
    }

}
