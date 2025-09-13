package org.example.music.exception;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    // You might also need a default constructor and setters for some JSON frameworks
    public ErrorResponse() {
    }

    public void setMessage(String message) {
        this.message = message;
    }
}