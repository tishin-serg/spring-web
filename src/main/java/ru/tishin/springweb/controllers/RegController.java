package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.dto.ProfileDto;
import ru.tishin.springweb.dto.UserDto;
import ru.tishin.springweb.entities.User;
import ru.tishin.springweb.exceptions.AppError;
import ru.tishin.springweb.services.UserService;
import ru.tishin.springweb.utils.MapUtils;
import ru.tishin.springweb.validators.UserValidator;

/*
1. Принять с фронта дтошку с логином, паролем и имейлом.
2. Преобразовать ДТО в юзера. Сервис кладёт юзера в бд.
3. Сделать на фронте форму.
4. Сделать валидацию полей как на продукте.
5. Подвязать логику на js
6. Реализовать ошибку, если такой пользователь уже есть.
 */

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @PostMapping
    public ResponseEntity<?> registerNewProfile(@RequestBody UserDto userDto) {
        userValidator.validate(userDto);
        User user = MapUtils.fromUserDto(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userService.findByUserName(user.getUsername()).isEmpty()) {
            userService.save(user);
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), "User: " + user.getUsername() + " exist " +
                    "already"),
                    HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(MapUtils.toUserDto(user));
    }
}
