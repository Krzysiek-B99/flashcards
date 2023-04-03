package com.example.flashcards.service;

import com.example.flashcards.dto.SetPutDto;
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

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class SetService implements ISetService {

    private final SetRepository setRepository;
    private final UserRepository userRepository;
    private final MapStructMapper mapStructMapper;


    public SetService(SetRepository setRepository, UserRepository userRepository, MapStructMapper mapStructMapper) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
    }

    @Override
    public void addSet(String username, FlashcardSet set){

        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        setRepository.save(set);
        user.addSet(set);
        userRepository.save(user);
    }

    @Override
    public Set<SetSlimDto> getSets(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return mapStructMapper.flashcardSetsToSetsSlimDto(user.getSets());
    }

    @Override
    public FlashcardSet getSetById(Long id){
        return setRepository.findById(id)
                 .orElseThrow(SetNotFoundException::new);
    }

    @Override
    public void deleteSet(Long id){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(SetNotFoundException::new);
        setRepository.delete(set);
    }

    @Override
    public List<Flashcard> getFlashcardsToRepeat(Long id){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(SetNotFoundException::new);
        return set.getFlashcards().stream().filter(flashcard -> flashcard.getRepeatTime().isBefore(LocalDateTime.now())).toList();
    }
    @Override
    public SetSlimDto changeSetNameAndPrivacy(Long id, SetPutDto setPutDto){
        FlashcardSet set = setRepository.findById(id)
                .orElseThrow(SetNotFoundException::new);
        set.setName(setPutDto.getName());
        set.setPrivate(setPutDto.isPrivate());
        setRepository.save(set);
        return mapStructMapper.flashcardSetToSetSlimDto(setRepository.findById(id).orElseThrow(SetNotFoundException::new));

    }


}
