package com.example.flashcards;

import com.example.flashcards.entity.User;
import com.example.flashcards.repository.UserRepository;
import com.example.flashcards.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
            User user = new User();
            user.setUsername("user1");
            user.setPassword(passwordEncoder.getPasswordEncoder().encode("haslo1"));
            userRepository.save(user);
            User user2 = new User();
            user2.setUsername("user2");
            user2.setPassword(passwordEncoder.getPasswordEncoder().encode("haslo2"));
            userRepository.save(user2);

        };
    }
}
