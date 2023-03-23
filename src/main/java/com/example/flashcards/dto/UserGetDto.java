package com.example.flashcards.dto;

import com.example.flashcards.entity.FlashcardSet;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserGetDto {

    private Long id;

    private String username;

}
