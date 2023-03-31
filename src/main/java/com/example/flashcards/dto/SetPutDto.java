package com.example.flashcards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SetPutDto {
    @Size(max = 30,message = "max 30 signs")
    @NotNull
    private String name;

    private boolean isPrivate;
}
