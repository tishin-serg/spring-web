package ru.tishin.springweb.api.dto;

public class AppError {
    private String statusCode;
    private String message;

    public AppError(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public AppError() {
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
