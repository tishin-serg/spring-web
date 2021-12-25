package ru.tishin.springweb.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.dto.ProfileDto;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @GetMapping
    public ProfileDto getCurrentUserInfo(Principal principal) {
        return new ProfileDto(principal.getName());
    }
}
