package com.edu.active.services.storage.api;

import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Post;
import com.edu.active.services.storage.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;
//TODO add documentation
public interface PostStorageService {


    Optional<Resource<Post>> getpostById(long postId);

    Optional<Resource<Category>> getPostCategory(long postId);

    Optional<Page<Resource<User>>> getUsersLikePost(long postId, Pageable pageable);


    void addPostToUserLikedPosts(long postId, long userId);
}
