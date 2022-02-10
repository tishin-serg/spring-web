package ru.tishin.springweb.auth.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.api.dto.JwtResponse;
import ru.tishin.springweb.auth.converters.UserConverter;
import ru.tishin.springweb.auth.dto.UserDto;
import ru.tishin.springweb.auth.entities.User;
import ru.tishin.springweb.auth.services.RoleService;
import ru.tishin.springweb.auth.services.UserService;
import ru.tishin.springweb.auth.utils.JwtTokenUtil;
import ru.tishin.springweb.auth.validators.UserValidator;

@RestController
@RequestMapping("/api/v1/registration")
@RequiredArgsConstructor
public class RegController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserConverter userConverter;

    @PostMapping
    public ResponseEntity<?> registerNewProfile(@RequestBody UserDto userDto) {
        userValidator.validate(userDto);
        User user = userConverter.fromUserDto(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        roleService.setDefaultRole(user);
        userService.save(user);
        UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}