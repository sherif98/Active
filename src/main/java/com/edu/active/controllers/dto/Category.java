package com.edu.active.controllers.dto;

import com.edu.active.controllers.CategoriesController;
import com.edu.active.dao.entities.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.Resource;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class Category {
    private long id;

    private String categoryName;

    @JsonIgnore
    private Set<Post> posts;


    public static Resource<Category> getResource(CategoryEntity categoryEntity) {
        Category category = new Category(categoryEntity);
        Resource<Category> categoryResource = new Resource<>(category);
        categoryResource.add(linkTo(methodOn(CategoriesController.class).getCategoryById(category.getId())).withSelfRel());
        categoryResource.add(linkTo(methodOn(CategoriesController.class).getCategoryPostsById(category.getId()))
                .withRel("category_posts"));
        return categoryResource;
    }

    public Category(CategoryEntity categoryEntity) {
        this.id = categoryEntity.getId();
        this.categoryName = categoryEntity.getCategoryName();
        posts = new HashSet<>();
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
