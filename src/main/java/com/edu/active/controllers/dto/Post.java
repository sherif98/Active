package com.edu.active.controllers.dto;

import java.util.Set;

class Post {

    private long id;

    private String title;

    private String content;

    private Category category;

    private User ownerUser;

    private Set<User> usersLikePost;

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
