package com.example.flashcards.controller;

import com.example.flashcards.dto.SetPutDto;
import com.example.flashcards.dto.SetSlimDto;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.service.SetService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.net.URI;

@RestController
@RequestMapping("/sets")
public class SetController {

    private final SetService setService;

    public SetController(SetService setService) {
        this.setService = setService;
    }

    @PostMapping("")
    public ResponseEntity<?> addSet(@RequestBody FlashcardSet set, @AuthenticationPrincipal User user) {
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
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id)) or !(@setService.getSetById(#id).isPrivate)")
    public ResponseEntity<?> getSetById(@PathVariable Long id){
        return ResponseEntity.ok(setService.getSetById(id));
    }
    @PutMapping("/{id}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> editSet(@PathVariable Long id,@RequestBody @Valid SetPutDto setPutDto) {
        return ResponseEntity.ok().body(setService.changeSetNameAndPrivacy(id,setPutDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> deleteSet(@PathVariable Long id){
            setService.deleteSet(id);
            return ResponseEntity.ok().build();
    }
}
