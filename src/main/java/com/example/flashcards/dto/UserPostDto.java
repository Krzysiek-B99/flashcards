package com.example.flashcards.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPostDto {

    private String username;
    private String password;


    public UserPostDto(String username, String password) {
        this.username = username;
        this.password = password;

    }

}