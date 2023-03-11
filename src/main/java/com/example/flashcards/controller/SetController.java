package com.example.flashcards.controller;

import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.service.SetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> addSet(@RequestBody FlashcardSet set, Principal principal){
        setService.addSet(principal.getName(),set);
        return ResponseEntity.ok(set);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSets(Principal principal){
        return ResponseEntity.ok(setService.getSets(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSetByName(@PathVariable Long id, Principal principal){
        FlashcardSet set = setService.getSetById(id);
        User user = setService.getUserByUsername(principal.getName());
        return (!Objects.equals(user.getId(), set.getUser().getId()) && set.isPrivacy()) ? ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("this set is private") : ResponseEntity.ok(set);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSet(@PathVariable Long id, Principal principal){
        if(setService.deleteSet(id, principal.getName())){
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("this set doesnt belong to you");
    }
}
