package com.edu.active.controllers;

import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.Category;
import com.edu.active.dao.entities.Post;
import com.edu.active.dao.entities.User;
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
    public User getUserById(@PathVariable long id) {
        User user = usersRepository.findOne(id);
        return user;
    }

    @RequestMapping(name = "/{id}/posts", method = RequestMethod.GET)
    public Set<Post> getUserPosts(@PathVariable long id) {
        User user = usersRepository.findOne(id);
        return user.getCreatedPosts();
    }

    @RequestMapping(name = "/{userId}/posts", method = RequestMethod.POST)
    public void savePost(@RequestBody Post post, @PathVariable long userId, @RequestParam(name = "category") long categoryId) {
        Category category = categoriesRepository.findOne(categoryId);
        post.setCategory(category);
        User user = usersRepository.findOne(userId);
        post.setOwnerUser(user);
        postsRepository.save(post);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveUser(@RequestBody User user) {
        usersRepository.save(user);
    }


}
