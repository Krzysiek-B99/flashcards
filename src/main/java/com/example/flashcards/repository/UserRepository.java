package com.example.flashcards.repository;

import com.example.flashcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository  extends JpaRepository<User, UUID> {

    public Optional<User> findByUsername(String username);
    public Optional<User> findById(long id);
    public boolean existsByUsername(String username);
}
