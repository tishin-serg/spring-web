package ru.tishin.springweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель ответа на запрос аутентификации")
public class JwtResponse {

    @Schema(description = "Токен", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJib2IiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiZXhwIjoxNjQ3NDIwNTAyLCJpYXQiOjE2NDc0MjAxNDJ9.mDNWJlUvZpd8-XPi-6P14Mq-ZgImZ1eaLUuKXVZKanA")
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
