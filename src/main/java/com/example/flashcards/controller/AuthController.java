package com.example.flashcards.controller;

import com.example.flashcards.dto.UserPostDto;
import com.example.flashcards.entity.User;
import com.example.flashcards.mapper.MapStructMapper;
import com.example.flashcards.service.UserService;
import com.example.flashcards.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    private final MapStructMapper mapStructMapper;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, MapStructMapper mapStructMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.mapStructMapper = mapStructMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserPostDto userPostDto){
        if(userService.ifUserExist(userPostDto.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist");
        }
        userService.register(userPostDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapStructMapper.userPostDtoToUserGetDto(userPostDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserPostDto request){
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    );
            User user = (User) authenticate.getPrincipal();
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtUtil.generateToken(user)
                    )
                    .body(mapStructMapper.userToUserWithSetsDto(user));
        } catch (BadCredentialsException ex){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("wrong username or password");
        }

    }


}