package com.edu.active.controllers.dto;

import com.edu.active.controllers.UserController;
import com.edu.active.dao.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;
import org.springframework.hateoas.Resource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class User {

    private long id;

    @NotNull
    @Size(min = 5, max = 20)
    private String userName;

    @NotNull
    @Size(min = 5, max = 20)
    private String password;

    @Email
    @NotNull
    private String email;

    @JsonIgnore
    private Set<Category> categoriesFollowing;

    @JsonIgnore
    private Set<Post> createdPosts;

    @JsonIgnore
    private Set<Post> likedPosts;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Image image;

    public long getId() {
        return id;
    }


    public static Resource<User> getResource(UserEntity userEntity) {
        User user = new User(userEntity);
        Resource<User> userResource = new Resource<>(user);
        userResource.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        userResource.add(linkTo(methodOn(UserController.class).getUserPosts(user.getId())).withRel("created_posts"));
        userResource.add(linkTo(methodOn(UserController.class).categoriesFollowing(user.getId())).withRel("following_categories"));
        userResource.add(linkTo(methodOn(UserController.class).getLikedPosts(user.getId())).withRel("liked_posts"));
        userResource.add(linkTo(methodOn(UserController.class).getUserImage(userEntity.getId())).withRel("image"));
        return userResource;
    }

    public User(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.userName = userEntity.getUserName();
        this.password = userEntity.getPassword();
        this.email = userEntity.getEmail();
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

    @JsonIgnore
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
