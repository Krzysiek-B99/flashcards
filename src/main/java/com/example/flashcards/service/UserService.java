package com.example.flashcards.service;

import com.example.flashcards.dto.UserPostDto;
import com.example.flashcards.exception.SetNotFoundException;
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
    private final SetRepository setRepository;

    public UserService(UserRepository userRepository, CustomPasswordEncoder passwordEncoder, MapStructMapper mapStructMapper, SetRepository setRepository1) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapStructMapper = mapStructMapper;
        this.setRepository = setRepository1;
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
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }
    public Long getUserIdBySetId(Long setId){
        return setRepository.findById(setId).orElseThrow(SetNotFoundException::new).getUser().getId();
    }

}
