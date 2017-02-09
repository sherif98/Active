package com.edu.active.dao.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String categoryName;


    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostEntity> posts;

    @ManyToMany(mappedBy = "categoriesFollowing", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserEntity> usersFollowingCategory;

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

    public Set<PostEntity> getPosts() {
        return posts;
    }

    public void setPosts(Set<PostEntity> posts) {
        this.posts = posts;
    }

    public Set<UserEntity> getUsersFollowingCategory() {
        return usersFollowingCategory;
    }

    public void setUsersFollowingCategory(Set<UserEntity> usersFollowingCategory) {
        this.usersFollowingCategory = usersFollowingCategory;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
