package com.example.flashcards.service;

import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.exception.SetNotFoundException;
import com.example.flashcards.repository.FlashcardRepository;
import com.example.flashcards.repository.SetRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlashcardService {

    private final SetRepository setRepository;

    private final FlashcardRepository flashcardRepository;

    public FlashcardService(SetRepository setRepository, FlashcardRepository flashcardRepository) {
        this.setRepository = setRepository;
        this.flashcardRepository = flashcardRepository;
    }

    public void addFlashcardsToSet(Long id, List<Flashcard> flashcards){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(SetNotFoundException::new);
        for(Flashcard flashcard : flashcards) {
            flashcard.setLevel(1);
            flashcard.setRepeatTime(LocalDateTime.now());
            set.getFlashcards().add(flashcard);
            flashcardRepository.save(flashcard);
        }
        setRepository.save(set);
    }
    public Flashcard changeFlashcardsLevelBasedOnAnswer(Long id, boolean answer){
        Flashcard flashcard = flashcardRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("User not found"));
        if(answer){
           if(flashcard.getLevel()<5) {
               flashcard.setLevel(flashcard.getLevel() + 1);
           }
            switch (flashcard.getLevel()) {
                case 2 -> flashcard.setRepeatTime(LocalDateTime.now().plusDays(2));
                case 3 -> flashcard.setRepeatTime(LocalDateTime.now().plusDays(4));
                case 4 -> flashcard.setRepeatTime(LocalDateTime.now().plusDays(7));
                case 5 -> flashcard.setRepeatTime(LocalDateTime.now().plusDays(14));
            }
           flashcardRepository.save(flashcard);
           return flashcard;
        }
            flashcard.setLevel(1);
            flashcard.setRepeatTime(LocalDateTime.now().plusDays(1));
            flashcardRepository.save(flashcard);
            return flashcard;

    }
    public void deleteFlashcardFromSet(Long id,Long flashcardId){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(SetNotFoundException::new);
        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(()->new UsernameNotFoundException("User not found"));
        set.removeFlashcard(flashcard);
        flashcardRepository.delete(flashcard);
    }
}
