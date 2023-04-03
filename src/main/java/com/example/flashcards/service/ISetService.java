package com.example.flashcards.service;

import com.example.flashcards.dto.SetPutDto;
import com.example.flashcards.dto.SetSlimDto;
import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

public interface ISetService {
    void addSet(String username,@Valid FlashcardSet set);

    Set<SetSlimDto> getSets(String username);

    FlashcardSet getSetById(Long id);

    void deleteSet(Long id);

    List<Flashcard> getFlashcardsToRepeat(Long id);

    SetSlimDto changeSetNameAndPrivacy(Long id, SetPutDto setPutDto);
}
