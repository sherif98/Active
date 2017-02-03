package com.edu.active.controllers.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    private String resourceName;

    public ResourceAlreadyExistsException(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
