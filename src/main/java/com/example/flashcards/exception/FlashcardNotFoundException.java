package com.example.flashcards.exception;

public class FlashcardNotFoundException extends RuntimeException {

    private static final String EXCEPTION_MESSAGE = "Flashcard not found";
    public FlashcardNotFoundException() {

        super(EXCEPTION_MESSAGE);

    }
}
