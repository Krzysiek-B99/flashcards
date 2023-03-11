package com.example.flashcards.service;

import com.example.flashcards.entity.FlashcardSet;
import com.example.flashcards.entity.RegisterUser;
import com.example.flashcards.entity.User;
import com.example.flashcards.repository.SetRepository;
import com.example.flashcards.repository.UserRepository;
import com.example.flashcards.util.CustomPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;
    private final SetRepository setRepository;

    public UserService(UserRepository userRepository, CustomPasswordEncoder passwordEncoder, SetRepository setRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.setRepository = setRepository;
    }

    public User register(RegisterUser registerUser){
        User user = new User(registerUser.getUsername(),passwordEncoder.getPasswordEncoder().encode(registerUser.getPassword()));
        userRepository.save(user);
        return user;
    }

    public boolean ifUserExist(String username){
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException(""));
    }

}
