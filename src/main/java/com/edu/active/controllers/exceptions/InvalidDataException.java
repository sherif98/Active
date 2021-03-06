package com.edu.active.controllers.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class InvalidDataException extends RuntimeException {
    private StringBuilder message;

    public InvalidDataException(List<ObjectError> allErrors) {
        message = new StringBuilder();
        allErrors.forEach(e -> message.append(e.getCode() + e.getDefaultMessage()));
    }

    public String getMessage() {
        return message.toString();
    }
}
