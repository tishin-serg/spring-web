package ru.tishin.springweb.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель ошибки МС cart-service")
public class CartServiceAppError extends AppError {

    public enum CartServiceErrors {
        CART_NOT_FOUND, CART_SERVICE_REDIS_CONNECTION_ERROR, CART_SERVICE_CONNECTION_ERROR
    }

    public CartServiceAppError(String statusCode, String message) {
        super(statusCode, message);
    }

}
