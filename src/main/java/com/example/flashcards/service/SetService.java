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

    public String deleteSet(String setName, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(""));
        FlashcardSet set = setRepository.findByName(setName)
                .orElseThrow(()->new UsernameNotFoundException(""));
        if(!Objects.equals(user.getId(), set.getUser().getId())) {
            return "not authorized";
        }
        setRepository.delete(set);
        return "deleted";

    }
}
