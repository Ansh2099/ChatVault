package com.ansh.ChatVault_Backend.Exceptions;

import com.ansh.ChatVault_Backend.Exceptions.CustomExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class,
            MessageNotFoundException.class,
            ChatNotFoundException.class,
            UnauthorizedAccess.class,
            DuplicateUserException.class,
            FileUploadError.class})
    public ResponseEntity<ErrorResponse> customExceptions(RuntimeException exception){

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if(exception instanceof UserNotFoundException || exception instanceof MessageNotFoundException
                || exception instanceof ChatNotFoundException){
            status = HttpStatus.NOT_FOUND;

        } else if (exception instanceof UnauthorizedAccess) {
            status = HttpStatus.FORBIDDEN;

        } else if (exception instanceof DuplicateUserException) {
            status = HttpStatus.CONFLICT;

        } else if (exception instanceof FileUploadError) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        ErrorResponse error = new ErrorResponse(status.value(), exception.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> fallbackExceptions(Exception exception){

        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An Unexpected Error has occured",
                LocalDateTime.now());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
