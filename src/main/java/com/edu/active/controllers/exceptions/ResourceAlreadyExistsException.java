package com.edu.active.controllers.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    private ResourceType resourceType;
    private long resourceId;

    public ResourceAlreadyExistsException(ResourceType resourceType, long resourceId) {
        super(resourceType.toString() + " " + resourceId + " Already Exists");
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
