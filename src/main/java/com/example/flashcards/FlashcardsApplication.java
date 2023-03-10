package com.example.flashcards;

import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.repository.FlashcardRepository;
import com.example.flashcards.repository.SetRepository;
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
    public CommandLineRunner fillUserDB(UserRepository userRepository, FlashcardRepository flashcardRepository, SetRepository setRepository){
        return args -> {

            Flashcard flashcard = new Flashcard("front","back");
            flashcardRepository.save(flashcard);
            FlashcardSet set = new FlashcardSet("set1");
            set.getFlashcards().add(flashcard);
            setRepository.save(set);
            User user = new User("user1",passwordEncoder.getPasswordEncoder().encode("haslo1"));
            user.getSets().add(set);
            userRepository.save(user);



        };
    }
}
