package com.edu.active.controllers.exceptions;

public class ResourceNotFoundException extends RuntimeException {


    private ResourceType resourceType;
    private long resourceId;

    public ResourceNotFoundException(ResourceType resourceType, long resourceId) {
        super(resourceType.toString() + " " + resourceId + " Not Found");
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public long getResourceId() {
        return resourceId;
    }
}
