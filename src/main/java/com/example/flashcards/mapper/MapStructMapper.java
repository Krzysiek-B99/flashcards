package com.example.flashcards.mapper;

import com.example.flashcards.dto.UserGetDto;
import com.example.flashcards.dto.UserPostDto;
import com.example.flashcards.entity.User;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {
    User userPostDtoToUser(UserPostDto userPostDto);

    UserGetDto userToUserGetDto(User user);
}
