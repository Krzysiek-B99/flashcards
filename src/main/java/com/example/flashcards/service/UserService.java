package com.example.flashcards.service;

import com.example.flashcards.dto.UserPostDto;
import com.example.flashcards.entity.User;
import com.example.flashcards.mapper.MapStructMapper;
import com.example.flashcards.repository.SetRepository;
import com.example.flashcards.repository.UserRepository;
import com.example.flashcards.util.CustomPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final MapStructMapper mapStructMapper;
    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CustomPasswordEncoder passwordEncoder, SetRepository setRepository, MapStructMapper mapStructMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapStructMapper = mapStructMapper;
    }

    public void register(UserPostDto userPostDto){
        userPostDto.setPassword(passwordEncoder.getPasswordEncoder().encode(userPostDto.getPassword()));
        userRepository.save(mapStructMapper.userPostDtoToUser(userPostDto));
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
