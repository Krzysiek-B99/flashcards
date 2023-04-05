package com.example.flashcards.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.persistence.ElementCollection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    private int code;

    private String status;

    @ElementCollection
    private List<String> messages;

    private String stackTrace;

    private Object data;

    public ErrorResponse() {
        timestamp = new Date();
    }

    public ErrorResponse(
            HttpStatus httpStatus,
            List<String> messages
    ) {
        this();

        this.code = httpStatus.value();
        this.status = httpStatus.name();
        this.messages = messages;
    }

    public ErrorResponse(
            HttpStatus httpStatus,
            List<String> messages,
            String stackTrace
    ) {
        this(
                httpStatus,
                messages
        );

        this.stackTrace = stackTrace;
    }

    public ErrorResponse(
            HttpStatus httpStatus,
            List<String> messages,
            String stackTrace,
            Object data
    ) {
        this(
                httpStatus,
                messages,
                stackTrace
        );

        this.data = data;
    }
}