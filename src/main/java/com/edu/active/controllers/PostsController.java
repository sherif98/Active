package com.edu.active.controllers;

import com.edu.active.services.storage.api.PostStorageService;
import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Post;
import com.edu.active.services.storage.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/post")
@CrossOrigin
public class PostsController {


    @Autowired
    private PostStorageService postStorageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<Post> getPost(@PathVariable long id) {
        return postStorageService.getpostById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post " + id));
    }

    @RequestMapping(value = "/{id}/category", method = RequestMethod.GET)
    public Resource<Category> getPostCategory(@PathVariable long id) {

        return postStorageService.getPostCategory(id)
                .orElseThrow(() -> new ResourceNotFoundException("post " + id));
    }

    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET)
    public Page<Resource<User>> getUsersLikePost(
            @PathVariable long id,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        return postStorageService.getUsersLikePost(id, pageable)
                .orElseThrow(() -> new ResourceNotFoundException("post " + id));
    }

    @RequestMapping(value = "/{postId}/likes", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void likePost(@PathVariable long postId, @RequestParam(name = "userId") long userId) {
        postStorageService.addPostToUserLikedPosts(postId, userId);
    }
}
