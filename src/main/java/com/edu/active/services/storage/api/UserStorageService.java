package com.edu.active.services.storage.api;


import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Image;
import com.edu.active.services.storage.model.Post;
import com.edu.active.services.storage.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;

import java.util.Optional;

//TODO add documentation

public interface UserStorageService {

    Optional<Resource<User>> getUserById(long userId);

    Optional<Resource<Image>> getUserImage(long userId);

    Optional<Page<Resource<Post>>> getUserPosts(long userId, Pageable pageable);

    Optional<Page<Resource<Category>>> getUserFollowingCategories(long userId, Pageable pageable);


    Optional<Page<Resource<Post>>> getUserLikedPosts(long userId, Pageable pageable);


    void addPostToUserCreatedPosts(long userId, Post post, long categoryId);

    void registerNewUser(User user);

    void addCategoryToUserFollowingCategories(long userId, long categoryId);
}
