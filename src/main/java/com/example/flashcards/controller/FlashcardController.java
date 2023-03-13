package com.example.flashcards.controller;

import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.service.FlashcardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping("/{id}/flashcards")
    public ResponseEntity<?> addFlashardsToSet (@PathVariable Long id, @RequestBody List<Flashcard> flashcards, Principal principal){
        if(flashcardService.addFlashcardsToSet(id, flashcards,principal.getName())){
            return ResponseEntity.ok(flashcards);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("this set doesnt belong to you");
    }
}
