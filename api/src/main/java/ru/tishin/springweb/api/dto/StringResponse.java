package ru.tishin.springweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель ответа")
public class StringResponse {
    @Schema(description = "Содержимое ответа")
    private String value;

    public StringResponse(String value) {
        this.value = value;
    }

    public StringResponse() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
