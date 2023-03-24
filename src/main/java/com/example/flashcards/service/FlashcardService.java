package com.example.flashcards.service;

import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.repository.FlashcardRepository;
import com.example.flashcards.repository.SetRepository;
import com.example.flashcards.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class FlashcardService {

    private final UserRepository userRepository;
    private final SetRepository setRepository;

    private final FlashcardRepository flashcardRepository;

    public FlashcardService(UserRepository userRepository, SetRepository setRepository, FlashcardRepository flashcardRepository) {
        this.userRepository = userRepository;
        this.setRepository = setRepository;
        this.flashcardRepository = flashcardRepository;
    }

    public void addFlashcardsToSet(Long setId, List<Flashcard> flashcards){
        FlashcardSet set = setRepository.findById(setId)
                .orElseThrow(()->new UsernameNotFoundException("no such set in database"));
        for(Flashcard flashcard : flashcards) {
            flashcard.setLevel(1);
            flashcard.setRepeatTime(LocalDateTime.now());
            set.getFlashcards().add(flashcard);
            flashcardRepository.save(flashcard);
        }
        setRepository.save(set);
    }
    public Flashcard changeFlashcardsLevelBasedOnAnswer(Long id, boolean answer){
        Flashcard flashcard = flashcardRepository.findById(id).orElseThrow(()->new UsernameNotFoundException(""));
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
}
