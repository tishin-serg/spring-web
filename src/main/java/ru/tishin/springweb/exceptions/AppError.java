package ru.tishin.springweb.exceptions;

import ru.tishin.springweb.responses.ResponseDelivery;

public class AppError extends ResponseDelivery {
    public AppError(int statusCode, String message) {
        super(statusCode, message);
    }
}
