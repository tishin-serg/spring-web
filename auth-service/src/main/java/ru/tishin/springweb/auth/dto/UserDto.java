package ru.tishin.springweb.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Модель пользователя")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Schema(description = "Логин", example = "Bob")
    private String username;

    @Schema(description = "Пароль", example = "sjdk12312")
    private String password;

    @Schema(description = "Электронная почта", example = "bob@gmail.com")
    private String email;
}
