package com.edu.active.services.storage.impl;

import com.edu.active.controllers.exceptions.ResourceAlreadyExistsException;
import com.edu.active.controllers.exceptions.ResourceNotFoundException;
import com.edu.active.controllers.exceptions.ResourceType;
import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.ImageEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import com.edu.active.services.storage.api.UserStorageService;
import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Image;
import com.edu.active.services.storage.model.Post;
import com.edu.active.services.storage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserStorageServiceImpl implements UserStorageService {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public Resource<User> getUserById(long userId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            throw new ResourceNotFoundException(ResourceType.USER, userId);
        }
        return User.getResource(userEntity);
    }

    @Override
    public Resource<Image> getUserImage(long userId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            throw new ResourceNotFoundException(ResourceType.USER, userId);
        }
        ImageEntity imageEntity = userEntity.getImageEntity();
        Resource<Image> imageResource = Image.getResource(imageEntity);
        return imageResource;
    }

    @Override
    public Page<Resource<Post>> getUserPosts(long userId, Pageable pageable) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            throw new ResourceNotFoundException(ResourceType.USER, userId);
        }
        Page<PostEntity> page = postsRepository.findPostsByOwnerUser(userEntity, pageable);
        return page.map(Post::getResource);
    }

    @Override
    public Page<Resource<Category>> getUserFollowingCategories(long userId, Pageable pageable) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            throw new ResourceNotFoundException(ResourceType.USER, userId);
        }
        Page<CategoryEntity> categoryEntityPage = categoriesRepository.findCategoriesByUsersFollowingCategoryContaining(userEntity, pageable);
        return categoryEntityPage.map(Category::getResource);
    }

    @Override
    public Page<Resource<Post>> getUserLikedPosts(long userId, Pageable pageable) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            throw new ResourceNotFoundException(ResourceType.USER, userId);
        }
        Page<PostEntity> postEntityPage = postsRepository.findPostsByUsersLikePostContaining(userEntity, pageable);
        return postEntityPage.map(Post::getResource);
    }

    @Override
    public void addPostToUserCreatedPosts(long userId, Post post, long categoryId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            throw new ResourceNotFoundException(ResourceType.USER, userId);
        }
        CategoryEntity categoryEntity = categoriesRepository.findOne(categoryId);
        if (categoryEntity == null) {
            throw new ResourceNotFoundException(ResourceType.CATEGORY, categoryId);
        }

        PostEntity postEntity = new PostEntity(post, userEntity, categoryEntity);
        userEntity.getCreatedPosts().add(postEntity);
        categoryEntity.getPosts().add(postEntity);

        usersRepository.save(userEntity);
        categoriesRepository.save(categoryEntity);

    }

    @Override
    public void registerNewUser(User user) {
        UserEntity userEntity = new UserEntity(user);
        if (userExists(userEntity)) {
            throw new ResourceAlreadyExistsException(ResourceType.USER, userEntity.getId());
        }
        usersRepository.save(userEntity);
    }

    private boolean userExists(UserEntity userEntity) {
        return usersRepository.exists(userEntity.getId());
    }

    @Override
    public void addCategoryToUserFollowingCategories(long userId, long categoryId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            throw new ResourceNotFoundException(ResourceType.USER, userId);
        }
        CategoryEntity categoryEntity = categoriesRepository.findOne(categoryId);
        if (categoryEntity == null) {
            throw new ResourceNotFoundException(ResourceType.CATEGORY, categoryId);
        }
        Set<CategoryEntity> categoryEntities = userEntity.getCategoriesFollowing();
        categoryEntities.add(categoryEntity);
        usersRepository.save(userEntity);
    }
}
