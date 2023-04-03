package com.example.flashcards.service;

import com.example.flashcards.dto.FlashcardPutDto;
import com.example.flashcards.entity.Flashcard;

import java.util.List;

public interface IFlashcardService {
    void addFlashcardsToSet(Long id, List<Flashcard> flashcards);

    Flashcard changeFlashcardsLevelBasedOnAnswer(Long id, boolean answer);

    void deleteFlashcardFromSet(Long id, Long flashcardId);

    Flashcard changeFlashcardsFrontAndBack(FlashcardPutDto flashcardPutDto, Long id);
}
