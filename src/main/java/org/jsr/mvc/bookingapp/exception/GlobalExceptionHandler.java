package org.jsr.mvc.bookingapp.exception;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException e) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        e.getMessage(),
                        HttpStatus.NOT_FOUND.value(),
                        LocalDateTime.now()
                ),
                HttpStatus.NOT_FOUND
        );
    }
}
