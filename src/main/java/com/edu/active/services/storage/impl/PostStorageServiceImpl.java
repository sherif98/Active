package com.edu.active.services.storage.impl;

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

import java.util.Optional;

@Service
public class PostStorageServiceImpl implements PostStorageService {


    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;


    @Override
    public Optional<Resource<Post>> getpostById(long postId) {
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
            return Optional.empty();
        }
        Resource<Post> postResource = Post.getResource(postEntity);
        return Optional.ofNullable(postResource);
    }

    @Override
    public Optional<Resource<Category>> getPostCategory(long postId) {
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
            return Optional.empty();
        }
        CategoryEntity categoryEntity = postEntity.getCategory();
        Resource<Category> categoryResource = Category.getResource(categoryEntity);
        return Optional.ofNullable(categoryResource);
    }

    @Override
    public Optional<Page<Resource<User>>> getUsersLikePost(long postId, Pageable pageable) {
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
            return Optional.empty();
        }
        Page<UserEntity> userEntityPage = usersRepository.findUsersByLikedPostsContaining(postEntity, pageable);
        return Optional.ofNullable(userEntityPage.map(User::getResource));
    }

    @Override
    public void addPostToUserLikedPosts(long postId, long userId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
//TODO
//            userNotFound(userId);
        }
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
//            postNotFound(postId);
        }
        userEntity.getLikedPosts().add(postEntity);
        usersRepository.save(userEntity);
    }
}
