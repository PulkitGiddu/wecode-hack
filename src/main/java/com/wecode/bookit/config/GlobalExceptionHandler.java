package com.wecode.bookit.config;

import com.wecode.bookit.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            WebRequest request) {

        String message = ex.getMessage();
        String errorType = "REQUEST_PARSING_ERROR";
        String details = "Invalid request format";

        if (message != null && message.contains("UUID")) {
            errorType = "UUID_FORMAT_ERROR";
            details = "UUID must be in format: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx (36 characters)";
        }

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Failed to parse request")
                .errorType(errorType)
                .details(details)
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(
            RuntimeException ex,
            WebRequest request) {

        String message = ex.getMessage();
        String errorType = "RUNTIME_ERROR";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (message != null) {
            if (message.contains("Insufficient credits")) {
                errorType = "INSUFFICIENT_CREDITS";
                status = HttpStatus.BAD_REQUEST;
            } else if (message.contains("Lock expires in")) {
                errorType = "LOCK_CONFLICT";
                status = HttpStatus.CONFLICT;
            } else if (message.contains("not found")) {
                errorType = "NOT_FOUND";
                status = HttpStatus.NOT_FOUND;
            } else if (message.contains("Access denied")) {
                errorType = "ACCESS_DENIED";
                status = HttpStatus.FORBIDDEN;
            }
        }

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .statusCode(status.value())
                .message(message)
                .errorType(errorType)
                .details(ex.getClass().getSimpleName())
                .timestamp(LocalDateTime.now())
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}

