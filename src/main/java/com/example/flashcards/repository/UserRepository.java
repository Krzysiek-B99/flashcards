package com.example.flashcards.repository;

import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository  extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);
    public Optional<User> findById(long id);
    public boolean existsByUsername(String username);

}
