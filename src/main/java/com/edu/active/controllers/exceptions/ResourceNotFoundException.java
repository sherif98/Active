package com.edu.active.controllers.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;

    public ResourceNotFoundException(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
