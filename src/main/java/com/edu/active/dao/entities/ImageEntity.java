package com.edu.active.dao.entities;

import com.edu.active.controllers.dto.Image;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    @Column(length = 2097152)
    private byte[] binaryImage;

    public ImageEntity(Image image) {
        this.id = image.getId();
        this.binaryImage = image.getBinaryImage();
    }

    public ImageEntity() {
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

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                '}';
    }
}
