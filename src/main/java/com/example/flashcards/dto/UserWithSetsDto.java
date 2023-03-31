package com.example.flashcards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Setter
@Getter
public class UserWithSetsDto {
        private Long id;

        private String username;

        private Set<SetSlimDto> sets;

}
