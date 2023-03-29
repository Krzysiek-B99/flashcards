package com.example.flashcards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UserPostDto {

    @NotNull
    private String username;
    @NotNull
    private String password;


    public UserPostDto(String username, String password) {
        this.username = username;
        this.password = password;

    }

}