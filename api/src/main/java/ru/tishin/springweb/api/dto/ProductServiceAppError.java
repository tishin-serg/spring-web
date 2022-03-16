package ru.tishin.springweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель ошибки МС core-service")
public class ProductServiceAppError extends AppError {

    public enum ProductServiceErrors {
        PRODUCT_NOT_FOUND, PRODUCT_SERVICE_CONNECTION_ERROR
    }

    public ProductServiceAppError(String statusCode, String message) {
        super(statusCode, message);
    }

    public ProductServiceAppError() {
    }
}
