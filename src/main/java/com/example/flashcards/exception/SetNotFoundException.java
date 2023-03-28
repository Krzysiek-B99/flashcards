package com.example.flashcards.exception;

public class SetNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Set not found";
    public SetNotFoundException() {

        super(EXCEPTION_MESSAGE);

    }

}
