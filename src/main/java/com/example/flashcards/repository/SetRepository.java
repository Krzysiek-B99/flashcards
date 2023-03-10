package com.example.flashcards.repository;

import com.example.flashcards.entity.FlashcardSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SetRepository extends JpaRepository<FlashcardSet, UUID> {
}
