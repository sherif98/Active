package com.edu.active.dao.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;

    private String password;

    @OneToMany(mappedBy = "ownerUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Post> createdPosts;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> likedPosts;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Category> categoriesFollowing;


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

    public Set<Category> getCategoriesFollowing() {
        return categoriesFollowing;
    }

    public void setCategoriesFollowing(Set<Category> categoriesFollowing) {
        this.categoriesFollowing = categoriesFollowing;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", categoriesFollowing=" + categoriesFollowing.stream().map(Category::getCategoryName) +
                '}';
    }
}