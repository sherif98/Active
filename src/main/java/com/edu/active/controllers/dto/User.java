package com.edu.active.controllers.dto;

import com.edu.active.dao.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class User {

    private long id;

    private String userName;

    private String password;

    @JsonIgnore
    private Set<Category> categoriesFollowing;

    @JsonIgnore
    private Set<Post> createdPosts;

    @JsonIgnore
    private Set<Post> likedPosts;

    public long getId() {
        return id;
    }

    public User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.userName = userEntity.getUserName();
        this.password = userEntity.getPassword();
        categoriesFollowing = new HashSet<>();
        createdPosts = new HashSet<>();
        likedPosts = new HashSet<>();
    }

    public User() {
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

    public Set<Category> getCategoriesFollowing() {
        return categoriesFollowing;
    }

    public void setCategoriesFollowing(Set<Category> categoriesFollowing) {
        this.categoriesFollowing = categoriesFollowing;
    }

    public Set<Post> getCreatedPosts() {
        return createdPosts;
    }

    public void setCreatedPosts(Set<Post> createdPosts) {
        this.createdPosts = createdPosts;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }
}
