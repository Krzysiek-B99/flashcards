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

    public boolean addFlashcardsToSet(Long setId, List<Flashcard> flashcards, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(""));
        FlashcardSet set = setRepository.findById(setId)
                .orElseThrow(()->new UsernameNotFoundException("no such set in database"));
        if(!Objects.equals(user.getId(), set.getUser().getId())) {
            return false;
        }
        for(Flashcard flashcard : flashcards) {
            flashcard.setLevel(1);
            flashcard.setRepeatTime(LocalDateTime.now());
            set.getFlashcards().add(flashcard);
            flashcardRepository.save(flashcard);
        }
        setRepository.save(set);
        return true;
    }
}
