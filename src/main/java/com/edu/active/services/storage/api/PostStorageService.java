package com.edu.active.services.storage.api;

import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Post;
import com.edu.active.services.storage.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
//TODO add documentation
public interface PostStorageService {


    Resource<Post> getpostById(long postId);

    Resource<Category> getPostCategory(long postId);

    Page<Resource<User>> getUsersLikePost(long postId, Pageable pageable);


    void addPostToUserLikedPosts(long postId, long userId);
}
