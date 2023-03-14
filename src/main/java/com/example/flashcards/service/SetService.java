package com.example.flashcards.service;

import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.repository.SetRepository;
import com.example.flashcards.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    public FlashcardSet getSetById(Long setId){
        return setRepository.findById(setId)
                 .orElseThrow(() -> new UsernameNotFoundException("no such set in database"));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(""));
    }

    public boolean deleteSet(Long setId, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(""));
        FlashcardSet set = setRepository.findById(setId)
                .orElseThrow(()->new UsernameNotFoundException(""));
        if(!Objects.equals(user.getId(), set.getUser().getId())) {
            return false;
        }
        setRepository.delete(set);
        return true;

    }


}
