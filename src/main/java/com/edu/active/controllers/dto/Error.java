package com.edu.active.controllers.dto;

public class Error {
    private String message;

    public Error(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }
}
