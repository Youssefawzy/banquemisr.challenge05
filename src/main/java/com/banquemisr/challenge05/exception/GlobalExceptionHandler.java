package com.banquemisr.challenge05.exception;


import com.banquemisr.challenge05.response.ErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "failed",
                "Validation errors occurred: " + errorMessage
        );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        return buildErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "failed", ex.getMessage());
    }

    @ExceptionHandler(TaskAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleTaskAlreadyExistsException(TaskAlreadyExistsException ex) {
        return buildErrorResponse(ex.getStatusCode(), "failed", ex.getMessage());
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(TaskNotFoundException ex) {
        return buildErrorResponse(ex.getStatusCode(), "failed", ex.getMessage());
    }

    @ExceptionHandler(HistoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHistoryNotFoundException(HistoryNotFoundException ex) {
        return buildErrorResponse(ex.getStatusCode(), "failed", ex.getMessage());
    }

    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotificationNotFoundException(NotificationNotFoundException ex) {
        return buildErrorResponse(ex.getStatusCode(), "failed", ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return buildErrorResponse(ex.getStatusCode(), "failed", ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildErrorResponse(ex.getStatusCode(), "failed", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed", "An unexpected error occurred.");
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleUserAuthenticationException(UserAuthenticationException ex, WebRequest request) {
        return buildErrorResponse(ex.getStatusCode(), "failed", ex.getMessage());
    }

    @ExceptionHandler(TaskOwnerShipException.class)
    public ResponseEntity<ErrorResponse> handleTaskOwnershipException(TaskOwnerShipException ex, WebRequest request) {
        return buildErrorResponse(ex.getStatusCode(), "failed", ex.getMessage());
    }


    // Utility method to build a consistent error response
    private ResponseEntity<ErrorResponse> buildErrorResponse(int statusCode, String status, String message) {
        ErrorResponse errorResponse = new ErrorResponse(
                statusCode,
                status,
                message
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(statusCode));
    }
}

