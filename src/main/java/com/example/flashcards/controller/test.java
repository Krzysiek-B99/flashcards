package com.example.flashcards.controller;

import com.example.flashcards.entity.User;
import com.example.flashcards.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class test {

    @GetMapping("/{id}")
    @PreAuthorize("authentication.principal.id.equals(@userService.getUserIdBySetId(#id)) or !(@setService.getSetById(#id).privacy)")

    public String getSet(@AuthenticationPrincipal User user, @PathVariable Long id){
        return "test";
    }

}
