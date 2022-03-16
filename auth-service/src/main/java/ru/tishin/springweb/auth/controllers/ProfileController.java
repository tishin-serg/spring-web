package ru.tishin.springweb.auth.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tishin.springweb.api.dto.AppError;
import ru.tishin.springweb.api.dto.JwtResponse;
import ru.tishin.springweb.auth.dto.ProfileDto;

import java.security.Principal;

@Tag(name = "Профильный контроллер", description = "Отвечает за информацию о пользователе")
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    @Operation(
            summary = "Запрос на получение информации о пользователе",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProfileDto.class))
                    )
            })
    @GetMapping
    public ProfileDto getCurrentUserInfo(Principal principal) {
        return new ProfileDto(principal.getName());
    }
}
