package com.example.flashcards.handler;

import com.example.flashcards.error.ErrorResponse;
import com.example.flashcards.exception.SetNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(SetNotFoundException.class)
    public ResponseEntity<ErrorResponse> customHandleNotFound(Exception ex) {
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
