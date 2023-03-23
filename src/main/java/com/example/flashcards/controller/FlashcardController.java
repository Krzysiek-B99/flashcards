package com.example.flashcards.controller;

import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.service.FlashcardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sets")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping("/{id}/flashcards")
    public ResponseEntity<?> addFlashardsToSet (@PathVariable Long id, @RequestBody List<Flashcard> flashcards, @AuthenticationPrincipal User user){
        if(flashcardService.addFlashcardsToSet(id, flashcards, user.getUsername())){
            return ResponseEntity.ok(flashcards);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("this set doesnt belong to you");
    }
}
