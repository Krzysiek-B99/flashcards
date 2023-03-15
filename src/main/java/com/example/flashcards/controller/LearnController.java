package com.example.flashcards.controller;

import com.example.flashcards.entity.User;
import com.example.flashcards.service.SetService;
import org.aspectj.weaver.ResolvedPointcutDefinition;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learn")
public class LearnController {

    private final SetService setService;

    public LearnController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> learnSetById(@PathVariable Long id, @AuthenticationPrincipal User user){
       return ResponseEntity.ok(setService.getFlashcardsToRepeat(id));
    }
}
