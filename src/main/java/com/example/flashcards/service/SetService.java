package com.example.flashcards.service;

import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.repository.SetRepository;
import com.example.flashcards.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class SetService {

    private final SetRepository setRepository;
    private final UserRepository userRepository;


    public SetService(SetRepository setRepository, UserRepository userRepository) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;

    }

    public void addSet(String username, FlashcardSet set){

        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("login error"));
        setRepository.save(set);
        user.addSet(set);
        userRepository.save(user);
    }

    public Set<FlashcardSet> getSets(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("login error"));
        return user.getSets();
    }

    public FlashcardSet getSetById(Long id){
        return setRepository.findById(id)
                 .orElseThrow(() -> new UsernameNotFoundException("no such set in database"));
    }

    public boolean deleteSet(Long id, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(""));
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException(""));
        if(!Objects.equals(user.getId(), set.getUser().getId())) {
            return false;
        }
        setRepository.delete(set);
        return true;
    }

    public List<Flashcard> getFlashcardsToRepeat(Long id){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException(""));
        return set.getFlashcards().stream().filter(flashcard -> flashcard.getRepeatTime().isBefore(LocalDateTime.now())).toList();
    }


}
