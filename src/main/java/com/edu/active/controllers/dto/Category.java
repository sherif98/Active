package com.edu.active.controllers.dto;

import com.edu.active.dao.entities.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

public class Category {
    private long id;

    private String categoryName;

    @JsonIgnore
    private Set<Post> posts;

    public Category(CategoryEntity categoryEntity) {
        this.id = categoryEntity.getId();
        this.categoryName = categoryEntity.getCategoryName();
    }

    public Category() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
