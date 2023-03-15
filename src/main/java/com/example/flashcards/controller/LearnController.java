package com.example.flashcards.controller;

import com.example.flashcards.entity.User;
import com.example.flashcards.service.FlashcardService;
import com.example.flashcards.service.SetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learn")
public class LearnController {

    private final SetService setService;

    private final FlashcardService flashcardService;

    public LearnController(SetService setService, FlashcardService flashcardService) {
        this.setService = setService;
        this.flashcardService = flashcardService;
    }

    @GetMapping("/sets/{setId}")
    public ResponseEntity<?> learnSetById(@PathVariable Long setId, @AuthenticationPrincipal User user){
       return ResponseEntity.ok(setService.getFlashcardsToRepeat(setId));
    }
    @PutMapping({"/flashcards/{flashcardId}/{answer}"})
    public ResponseEntity<?> ReceiveFlashcardAnswer(@PathVariable Long flashcardId, @PathVariable boolean answer, @AuthenticationPrincipal User user){
        return ResponseEntity.ok(flashcardService.changeFlashcardsLevelBasedOnAnswer(flashcardId,answer));
    }
}
