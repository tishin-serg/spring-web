package ru.tishin.springweb.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Модель информации о пользователе")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    @Schema(description = "Логин пользователя", example = "Bob")
    private String username;

}
