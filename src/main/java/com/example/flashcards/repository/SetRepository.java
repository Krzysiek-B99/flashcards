package com.example.flashcards.repository;

import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreFilter;

import java.util.Optional;

public interface SetRepository extends JpaRepository<FlashcardSet, Long> {


    public Optional<FlashcardSet> findByName(String name);
}
