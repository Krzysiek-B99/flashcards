package com.example.flashcards.controller;

import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SetController {

    private final UserService userService;

    public SetController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sets")
    public ResponseEntity<?> addSet(@RequestBody FlashcardSet set, Principal principal){
        userService.addSet(principal.getName(),set);
        return ResponseEntity.ok(set);
    }

    @GetMapping("/sets")
    public ResponseEntity<?> getSets(Principal principal){
        return ResponseEntity.ok(userService.getSets(principal.getName()));
    }
}
