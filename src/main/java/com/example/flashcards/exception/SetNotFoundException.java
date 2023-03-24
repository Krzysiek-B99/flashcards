package com.example.flashcards.exception;

import org.springframework.data.crossstore.ChangeSetPersister;

public class SetNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Set not found";
    public SetNotFoundException() {

        super(EXCEPTION_MESSAGE);

    }

}
