package com.example.flashcards.controller;

import com.example.flashcards.dto.FlashcardPutDto;
import com.example.flashcards.dto.SetPutDto;
import com.example.flashcards.entity.Flashcard;
import com.example.flashcards.service.FlashcardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<?> addFlashcardsToSet(@PathVariable Long id, @RequestBody List<Flashcard> flashcards){
        flashcardService.addFlashcardsToSet(id, flashcards);
        return ResponseEntity.ok(flashcards);

    }
    @DeleteMapping("/{id}/flashcards/{flashcardId}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> addFlashcardsToSet( @PathVariable Long id,@PathVariable Long flashcardId){
        flashcardService.deleteFlashcardFromSet(id,flashcardId);
        return ResponseEntity.ok().build();

    }

    @PutMapping("/{id}/flashcards/{flashcardId}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> editFlashcard(@PathVariable Long id, @PathVariable Long flashcardId, @RequestBody @Valid FlashcardPutDto flashcardPutDto) {
        return ResponseEntity.ok().body(flashcardService.changeFlashcardsFrontAndBack(flashcardPutDto,flashcardId));
    }

}
