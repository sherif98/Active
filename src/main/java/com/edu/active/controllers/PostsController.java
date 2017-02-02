package com.edu.active.controllers;

import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/post")
public class PostsController {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PostEntity getPost(@PathVariable long id) {
        return postsRepository.findOne(id);
    }

    @RequestMapping(value = "/{id}/category", method = RequestMethod.GET)
    public CategoryEntity getPostCategory(@PathVariable long id) {
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + id);
        PostEntity post = postsRepository.findOne(id);
        CategoryEntity category = post.getCategory();
        System.out.println(category);
        return category;
    }

    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET)
    public Set<UserEntity> getUsersLikePost(@PathVariable long id) {
        PostEntity post = postsRepository.findOne(id);
        return post.getUsersLikePost();
    }

    @RequestMapping(value = "/{postId}/likes", method = RequestMethod.PUT)
    public void likePost(@PathVariable long postId, @RequestParam(name = "userId") long userId) {
        UserEntity user = usersRepository.findOne(userId);
        PostEntity post = postsRepository.findOne(postId);
        user.getLikedPosts().add(post);
        usersRepository.save(user);
        //TODO
        //does it get added correctly ?
    }
}
