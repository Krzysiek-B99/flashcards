package com.example.flashcards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class SetSlimDto {

    private Long id;

    @Size(max = 30,message = "max 30 signs")
    private String name;

    private boolean isPrivate;
}
