package com.edu.active.dao.entities;

import com.edu.active.services.storage.model.Post;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    private String content;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity ownerUser;

    @ManyToMany(mappedBy = "likedPosts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserEntity> usersLikePost;


    public PostEntity(Post post, UserEntity userEntity, CategoryEntity categoryEntity) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.ownerUser = userEntity;
        this.category = categoryEntity;
    }

    public PostEntity(){}

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

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public UserEntity getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(UserEntity ownerUser) {
        this.ownerUser = ownerUser;
    }

    public Set<UserEntity> getUsersLikePost() {
        return usersLikePost;
    }

    public void setUsersLikePost(Set<UserEntity> usersLikePost) {
        this.usersLikePost = usersLikePost;
    }

    @Override
    public String toString() {
        return "PostEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category.getCategoryName() +
                ", ownerUser=" + ownerUser.getId() +
                '}';
    }
}
