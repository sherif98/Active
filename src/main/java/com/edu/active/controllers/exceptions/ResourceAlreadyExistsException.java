package com.edu.active.controllers.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    private String resourceName;

    public ResourceAlreadyExistsException(String message, String resourceName) {
        super(message);
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
