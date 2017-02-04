package com.edu.active.dao.entities;

import com.edu.active.controllers.dto.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CategoryEntity> categoriesFollowing;


    @OneToMany(mappedBy = "ownerUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PostEntity> createdPosts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PostEntity> likedPosts;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_image_id")
    private ImageEntity imageEntity;


    public UserEntity(User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.imageEntity = new ImageEntity(user.getImage());
    }

    public UserEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<PostEntity> getCreatedPosts() {
        return createdPosts;
    }

    public void setCreatedPosts(Set<PostEntity> createdPosts) {
        this.createdPosts = createdPosts;
    }

    public Set<PostEntity> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<PostEntity> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public Set<CategoryEntity> getCategoriesFollowing() {
        return categoriesFollowing;
    }

    public void setCategoriesFollowing(Set<CategoryEntity> categoriesFollowing) {
        this.categoriesFollowing = categoriesFollowing;
    }

    public ImageEntity getImageEntity() {
        return imageEntity;
    }

    public void setImageEntity(ImageEntity imageEntity) {
        this.imageEntity = imageEntity;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", categoriesFollowing=" + categoriesFollowing.stream().map(CategoryEntity::getCategoryName) +
                '}';
    }
}