package com.example.flashcards.controller;

import com.example.flashcards.config.LoginCredentials;
import com.example.flashcards.entity.RegisterUser;
import com.example.flashcards.entity.User;
import com.example.flashcards.service.UserService;
import com.example.flashcards.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUser registerUser){
        if(!registerUser.getPassword().equals(registerUser.getRepeatPassword()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        return userService.ifUserExist(registerUser.getUsername()) ?  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist") : ResponseEntity.ok(userService.register(registerUser));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginCredentials request){
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    );
            User user = (User) authenticate.getPrincipal();
            user.setPassword(null);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(user)
                    )
                    .body(user);
        } catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


}