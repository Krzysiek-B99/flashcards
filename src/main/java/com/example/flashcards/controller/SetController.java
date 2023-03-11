package com.example.flashcards.controller;

import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.service.SetService;
import com.example.flashcards.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class SetController {

    private final SetService setService;

    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping("/sets")
    public ResponseEntity<?> addSet(@RequestBody FlashcardSet set, Principal principal){
        setService.addSet(principal.getName(),set);
        return ResponseEntity.ok(set);
    }

    @GetMapping("/sets")
    public ResponseEntity<?> getSets(Principal principal){
        return ResponseEntity.ok(setService.getSets(principal.getName()));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteSet(@PathVariable String name, Principal principal){

        return ResponseEntity.ok(setService.deleteSet(name,principal.getName()));
    }
}
