package com.example.flashcards.handler;

import com.example.flashcards.error.ErrorResponse;
import com.example.flashcards.exception.FlashcardNotFoundException;
import com.example.flashcards.exception.SetNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({SetNotFoundException.class, UsernameNotFoundException.class, FlashcardNotFoundException.class})
    public ResponseEntity<ErrorResponse> customHandleSetNotFound(Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        Collections.singletonList(ex.getMessage())
                ),
                status
        );

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ErrorResponse> customValidationException(MethodArgumentNotValidException ex){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            errors.add(error.getDefaultMessage());
        }

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        errors

                ),
                status
        );

    }

}
