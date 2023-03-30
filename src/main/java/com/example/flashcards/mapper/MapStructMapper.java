package com.example.flashcards.mapper;

import com.example.flashcards.dto.SetSlimDto;
import com.example.flashcards.dto.UserGetDto;
import com.example.flashcards.dto.UserPostDto;
import com.example.flashcards.dto.UserWithSetsDto;
import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {
    User userPostDtoToUser(UserPostDto userPostDto);

    UserGetDto userPostDtoToUserGetDto(UserPostDto userPostDto);

    UserWithSetsDto userToUserWithSetsDto (User user);

    Set<SetSlimDto> flashcardSetsToSetsSlimDto(Set<FlashcardSet> flashcardSets);

    SetSlimDto flashcardSetToSetSlimDto(FlashcardSet flashcardSet);

    FlashcardSet setSlimDtoToFlashcardSet (SetSlimDto setSlimDto);

}
