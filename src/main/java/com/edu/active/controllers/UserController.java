package com.edu.active.controllers;

import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserEntity getUserById(@PathVariable long id) {
        UserEntity user = usersRepository.findOne(id);
        return user;
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public Set<PostEntity> getUserPosts(@PathVariable long id) {
        UserEntity user = usersRepository.findOne(id);
        return user.getCreatedPosts();
    }

    @RequestMapping(value = "/{id}categories", method = RequestMethod.GET)
    public Set<CategoryEntity> categoriesFollowing(@PathVariable long id) {
        return usersRepository.findOne(id).getCategoriesFollowing();
    }

    @RequestMapping(value = "/{userId}/posts", method = RequestMethod.POST)
    public void savePost(@RequestBody PostEntity post, @PathVariable long userId, @RequestParam(name = "category") long categoryId) {
        CategoryEntity category = categoriesRepository.findOne(categoryId);
        post.setCategory(category);
        UserEntity user = usersRepository.findOne(userId);
        post.setOwnerUser(user);
        postsRepository.save(post);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveUser(@RequestBody UserEntity user) {
        usersRepository.save(user);
    }


}
