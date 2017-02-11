package com.edu.active.services.storage.model;

import com.edu.active.controllers.UserController;
import com.edu.active.dao.entities.ImageEntity;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class Image {

    private long id;
    private byte[] binaryImage;


    public static Resource<Image> getResource(ImageEntity imageEntity) {
        Image image = new Image(imageEntity);
        Resource<Image> imageResource = new Resource<>(image);
        imageResource.add(linkTo(methodOn(UserController.class).getUserImage(image.getId())).withSelfRel());
        return imageResource;
    }

    public Image(ImageEntity imageEntity) {
        this.id = imageEntity.getId();
        this.binaryImage = imageEntity.getBinaryImage();
    }

    public Image() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getBinaryImage() {
        return binaryImage;
    }

    public void setBinaryImage(byte[] binaryImage) {
        this.binaryImage = binaryImage;
    }
}
