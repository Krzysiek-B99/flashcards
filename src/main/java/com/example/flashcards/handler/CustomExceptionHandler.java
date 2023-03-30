package com.example.flashcards.handler;

import com.example.flashcards.error.ErrorResponse;
import com.example.flashcards.exception.FlashcardNotFoundException;
import com.example.flashcards.exception.SetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({SetNotFoundException.class, UsernameNotFoundException.class, FlashcardNotFoundException.class})
    public ResponseEntity<ErrorResponse> customHandleSetNotFound(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        ex.getMessage()
                ),
                status
        );

    }

}
