package com.edu.active.services.storage.impl;

import com.edu.active.controllers.exceptions.GlobalExceptionHandlingController;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import com.edu.active.services.storage.api.PostStorageService;
import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Post;
import com.edu.active.services.storage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

@Service
public class PostStorageServiceImpl implements PostStorageService {


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;


    @Override
    public Resource<Post> getpostById(long postId) {
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
            GlobalExceptionHandlingController.postNotFound(postId);
        }
        Resource<Post> postResource = Post.getResource(postEntity);
        return postResource;
    }

    @Override
    public Resource<Category> getPostCategory(long postId) {
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
            GlobalExceptionHandlingController.postNotFound(postId);
        }
        CategoryEntity categoryEntity = postEntity.getCategory();
        Resource<Category> categoryResource = Category.getResource(categoryEntity);
        return categoryResource;
    }

    @Override
    public Page<Resource<User>> getUsersLikePost(long postId, Pageable pageable) {
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
            GlobalExceptionHandlingController.postNotFound(postId);
        }
        Page<UserEntity> userEntityPage = usersRepository.findUsersByLikedPostsContaining(postEntity, pageable);
        return userEntityPage.map(User::getResource);
    }

    @Override
    public void addPostToUserLikedPosts(long postId, long userId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            GlobalExceptionHandlingController.userNotFound(userId);
        }
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
            GlobalExceptionHandlingController.postNotFound(postId);
        }
        userEntity.getLikedPosts().add(postEntity);
        usersRepository.save(userEntity);
    }
}
