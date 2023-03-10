package com.example.flashcards;

import com.example.flashcards.entity.User;
import com.example.flashcards.repository.UserRepository;
import com.example.flashcards.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class FlashcardsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlashcardsApplication.class, args);
    }

    @Autowired
    private CustomPasswordEncoder passwordEncoder;
    @Bean
    public CommandLineRunner fillUserDB(UserRepository userRepository){
        return args -> {

            userRepository.save(new User("user1",passwordEncoder.getPasswordEncoder().encode("haslo1")));

        };
    }
}
