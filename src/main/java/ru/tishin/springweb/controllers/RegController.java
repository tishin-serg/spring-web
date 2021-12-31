package ru.tishin.springweb.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.dto.JwtResponse;
import ru.tishin.springweb.dto.UserDto;
import ru.tishin.springweb.entities.User;
import ru.tishin.springweb.services.RoleService;
import ru.tishin.springweb.services.UserService;
import ru.tishin.springweb.utils.JwtTokenUtil;
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
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final JwtTokenUtil jwtTokenUtil;
    private final MapUtils mapUtils;

    @PostMapping
    public ResponseEntity<?> registerNewProfile(@RequestBody UserDto userDto) {
        userValidator.validate(userDto);
        User user = mapUtils.fromUserDto(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleService.setDefaultRole(user);
        userService.save(user);
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
