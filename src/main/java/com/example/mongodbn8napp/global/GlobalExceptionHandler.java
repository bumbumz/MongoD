package com.example.mongodbn8napp.global;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.example.mongodbn8napp.global.exception.InvalidException;
import com.example.mongodbn8napp.global.exception.NotFoundException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý lỗi chung (Exception)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Lỗi hệ thống: " + ex.getMessage(),
                        null,
                        null));
    }

    // Xử lý lỗi tham số không hợp lệ (Validation)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Dữ liệu không hợp lệ: " + errorMessage,
                        null,
                        null));
    }

    // Xử lý lỗi ConstraintViolationException (Validation cho @Valid trên tham số)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Dữ liệu không hợp lệ: " + errorMessage,
                        null,
                        null));
    }

    // Xử lý lỗi ResponseStatusException (ví dụ: NOT_FOUND, BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(
                new ApiResponse<>(
                        ex.getStatusCode().value(),
                        ex.getReason(),
                        null,
                        null));
    }

    // Xử lý lỗi IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Dữ liệu không hợp lệ: " + ex.getMessage(),
                        null,
                        null));
    }

    // ============================================================================================================================
    // Xử lý NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse<>(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        null,
                        null));
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidException(InvalidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        ex.getMessage(),
                        null,
                        null));
    }

}
