package com.example.flashcards.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserPostDto {

    @NotNull
    @Size(min=5,max = 20,message = "invalid username: must by of 5-20 characters")
    private String username;
    @Size(min=5,max = 15,message = "invalid password: must by of 5-15 characters")
    @NotNull
    private String password;


    public UserPostDto(String username, String password) {
        this.username = username;
        this.password = password;

    }

}