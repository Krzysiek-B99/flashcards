package com.example.flashcards.controller;

import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.service.SetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/sets")
public class SetController {

    private final SetService setService;

    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping("")
    public ResponseEntity<?> addSet(@RequestBody FlashcardSet set, @AuthenticationPrincipal User user){
        setService.addSet(user.getUsername(), set);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(set.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSets(@AuthenticationPrincipal(expression = "username") String username){
        return ResponseEntity.ok(setService.getSets(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSetById(@PathVariable Long id, @AuthenticationPrincipal User user){
        FlashcardSet set = setService.getSetById(id);
        return (!Objects.equals(user.getId(), set.getUser().getId()) && set.isPrivacy()) ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("this set is private") : ResponseEntity.ok(set);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSet(@PathVariable Long id, @AuthenticationPrincipal User user){
        if(setService.deleteSet(id, user.getUsername())){
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
