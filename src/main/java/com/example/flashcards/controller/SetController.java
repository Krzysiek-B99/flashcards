package com.example.flashcards.controller;

import com.example.flashcards.dto.SetPutDto;
import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.User;
import com.example.flashcards.service.ISetService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/sets")
public class SetController {

    private final ISetService ISetService;

    public SetController(ISetService ISetService) {
        this.ISetService = ISetService;
    }

    @PostMapping("")
    public ResponseEntity<?> addSet(@RequestBody @Valid FlashcardSet set, @AuthenticationPrincipal User user) {
        ISetService.addSet(user.getUsername(), set);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(set.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("")
    public ResponseEntity<?> getAllSets(@AuthenticationPrincipal(expression = "username") String username){
        return ResponseEntity.ok(ISetService.getSets(username));
    }

    @GetMapping("/{id}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id)) or !(@setService.getSetById(#id).isPrivate)")
    public ResponseEntity<?> getSetById(@PathVariable Long id){
        return ResponseEntity.ok(ISetService.getSetById(id));
    }
    @PutMapping("/{id}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> editSet(@PathVariable Long id,@RequestBody @Valid SetPutDto setPutDto) {
        return ResponseEntity.ok().body(ISetService.changeSetNameAndPrivacy(id,setPutDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id))")
    public ResponseEntity<?> deleteSet(@PathVariable Long id){
            ISetService.deleteSet(id);
            return ResponseEntity.ok().build();
    }
}
