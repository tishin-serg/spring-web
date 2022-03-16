package ru.tishin.springweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель запроса на аутентификацию")
public class JwtRequest {

    @Schema(description = "Логин", example = "Bob")
    private String username;

    @Schema(description = "Пароль", example = "sdjk123")
    private String password;

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public JwtRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
