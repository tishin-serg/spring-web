package ru.tishin.springweb.auth.controllers.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tishin.springweb.auth.controllers.dto.UserDto;
import ru.tishin.springweb.auth.controllers.entities.User;

@Service
@RequiredArgsConstructor
public class MapUtils {


    public UserDto toUserDto(User user) {
        return new UserDto(user.getUsername(), user.getPassword(), user.getEmail());
    }

    public User fromUserDto(UserDto userDto) {
        return new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
    }

}
