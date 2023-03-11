package com.example.flashcards.repository;

import com.example.flashcards.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}
