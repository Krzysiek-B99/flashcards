package com.example.flashcards.controller;

import com.example.flashcards.service.FlashcardService;
import com.example.flashcards.service.SetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/sets/{id}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> learnSetById(@PathVariable Long id){
       return ResponseEntity.ok(setService.getFlashcardsToRepeat(id));
    }
    @PutMapping({"/sets/{id}/{flashcardId}/{answer}"})
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> ReceiveFlashcardAnswer(@PathVariable Long flashcardId, @PathVariable Long id,@PathVariable boolean answer){
        return ResponseEntity.ok(flashcardService.changeFlashcardsLevelBasedOnAnswer(flashcardId,answer));
    }
}
