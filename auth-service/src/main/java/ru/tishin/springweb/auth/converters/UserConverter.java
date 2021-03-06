package ru.tishin.springweb.auth.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.auth.dto.UserDto;
import ru.tishin.springweb.auth.entities.User;

@Service
@RequiredArgsConstructor
public class UserConverter {


    public UserDto toUserDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public User fromUserDto(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
    }

}
