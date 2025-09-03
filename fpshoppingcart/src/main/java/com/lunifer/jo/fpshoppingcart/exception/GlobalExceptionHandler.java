package com.lunifer.jo.fpshoppingcart.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                       WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDetail> handleDuplicateResourceException(DuplicateResourceException exception,
                                                                       WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorDetail> handleInvalidCredentialsException(InvalidCredentialsException exception,
                                                                       WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDetail> handleUnauthorizedException(UnauthorizedException exception,
                                                                  WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetail> handleBadCredentialsException(BadCredentialsException exception,
                                                                   WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                "Invalid credentials",
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

        @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleUsernameNotFoundException(UsernameNotFoundException exception,
                                                                      WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetail> handleAccessDeniedException(AccessDeniedException exception,
                                                                  WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                "Access denied. Insufficient permissions",
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetail> handleDataIntegrityViolationException(DataIntegrityViolationException exception,
                                                                           WebRequest webRequest) {
        String message = "Data integrity error";
        
        // Detect specific duplicate errors
        if (exception.getMessage().contains("Duplicate entry")) {
            if (exception.getMessage().contains("username")) {
                message = "Username is already in use";
            } else if (exception.getMessage().contains("email")) {
                message = "Email is already registered";
            } else {
                message = "Resource already exists";
            }
        }
        
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                message,
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.CONFLICT);
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> handleValidationExceptions(MethodArgumentNotValidException exception,
                                                                  WebRequest webRequest) {
        StringBuilder message = new StringBuilder("Validation error: ");
        
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                message.toString().trim(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDetail> handleTypeMismatchException(MethodArgumentTypeMismatchException exception,
                                                                   WebRequest webRequest) {
        String message = String.format("Parameter '%s' must be of type %s, received value: %s",
                exception.getName(),
                exception.getRequiredType().getSimpleName(),
                exception.getValue());
        
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                message,
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetail> handleIllegalArgumentException(IllegalArgumentException exception,
                                                                     WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorDetail errorDetail = new ErrorDetail(
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
