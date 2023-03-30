package com.example.flashcards.service;

import com.example.flashcards.dto.SetSlimDto;
import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.exception.SetNotFoundException;
import com.example.flashcards.mapper.MapStructMapper;
import com.example.flashcards.repository.SetRepository;
import com.example.flashcards.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class SetService {

    private final SetRepository setRepository;
    private final UserRepository userRepository;
    private final MapStructMapper mapStructMapper;


    public SetService(SetRepository setRepository, UserRepository userRepository, MapStructMapper mapStructMapper) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public void addSet(String username, FlashcardSet set){

        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        setRepository.save(set);
        user.addSet(set);
        userRepository.save(user);
    }

    public Set<SetSlimDto> getSets(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return mapStructMapper.flashcardSetsToSetsSlimDto(user.getSets());
    }

    public FlashcardSet getSetById(Long id){
        return setRepository.findById(id)
                 .orElseThrow(SetNotFoundException::new);
    }

    public void deleteSet(Long id){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(SetNotFoundException::new);
        setRepository.delete(set);
    }

    public List<Flashcard> getFlashcardsToRepeat(Long id){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(SetNotFoundException::new);
        return set.getFlashcards().stream().filter(flashcard -> flashcard.getRepeatTime().isBefore(LocalDateTime.now())).toList();
    }
    public SetSlimDto changeSetName(Long id,String name){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(SetNotFoundException::new);
        set.setName(name);
        setRepository.save(set);
        return mapStructMapper.flashcardSetToSetSlimDto(set);
    }


}
