package com.example.flashcards.controller;

import com.example.flashcards.service.FlashcardService;
import com.example.flashcards.service.ISetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learn")
public class LearnController {

    private final ISetService ISetService;

    private final FlashcardService IflashcardService;

    public LearnController(ISetService ISetService, FlashcardService IflashcardService) {
        this.ISetService = ISetService;
        this.IflashcardService = IflashcardService;
    }

    @GetMapping("/sets/{id}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> learnSetById(@PathVariable Long id){
       return ResponseEntity.ok(ISetService.getFlashcardsToRepeat(id));
    }
    @PutMapping({"/sets/{id}/{flashcardId}/{answer}"})
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> ReceiveFlashcardAnswer(@PathVariable Long flashcardId, @PathVariable Long id,@PathVariable boolean answer){
        return ResponseEntity.ok(IflashcardService.changeFlashcardsLevelBasedOnAnswer(flashcardId,answer));
    }
}
