package com.example.flashcards.controller;

import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.service.FlashcardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sets")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping("/{id}/flashcards")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> addFlashardsToSet (@PathVariable Long id, @RequestBody List<Flashcard> flashcards){
        flashcardService.addFlashcardsToSet(id, flashcards);
        return ResponseEntity.ok(flashcards);

    }
}
