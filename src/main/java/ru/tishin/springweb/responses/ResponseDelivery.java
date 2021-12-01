package ru.tishin.springweb.responses;

public class ResponseDelivery {
    private int statusCode;
    private String message;

    public ResponseDelivery(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseDelivery() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

}
