package com.edu.active.controllers.dto;

import com.edu.active.controllers.PostsController;
import com.edu.active.controllers.UserController;
import com.edu.active.dao.entities.PostEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.Resource;

import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class Post {

    private long id;

    private String title;

    private String content;

    @JsonIgnore
    private Category category;

    @JsonIgnore
    private User ownerUser;

    @JsonIgnore
    private Set<User> usersLikePost;


    public static Resource<Post> getResource(PostEntity postEntity) {
        Post post = new Post(postEntity);
        Resource<Post> postResource = new Resource<>(post);
        postResource.add(linkTo(methodOn(PostsController.class).getPost(post.getId())).withSelfRel());
        postResource.add(linkTo(methodOn(PostsController.class).getPostCategory(post.getId())).withRel("category"));
        postResource.add(linkTo(methodOn(UserController.class).getUserById(postEntity.getOwnerUser().getId())).withRel("post_owner"));
        postResource.add(linkTo(methodOn(PostsController.class).getUsersLikePost(post.getId())).withRel("likes"));
        return postResource;
    }

    public Post(PostEntity postEntity) {
        this.id = postEntity.getId();
        this.title = postEntity.getTitle();
        this.content = postEntity.getContent();
    }

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public Set<User> getUsersLikePost() {
        return usersLikePost;
    }

    public void setUsersLikePost(Set<User> usersLikePost) {
        this.usersLikePost = usersLikePost;
    }
}
