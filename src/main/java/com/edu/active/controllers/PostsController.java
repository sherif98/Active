package com.edu.active.controllers;

import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.entities.Category;
import com.edu.active.dao.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "/post")
public class PostsController {

    @Autowired
    private PostsRepository postsRepository;

    @RequestMapping(name = "/{id}", method = RequestMethod.GET)
    public Post getPost(@PathVariable long id) {
        return postsRepository.findOne(id);
    }

    @RequestMapping(name = "/{id}/category", method = RequestMethod.GET)
    public Category getPostCategory(@PathVariable long id) {
        Post post = postsRepository.findOne(id);
        return post.getCategory();
    }
}
