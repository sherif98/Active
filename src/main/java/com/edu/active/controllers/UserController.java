package com.edu.active.controllers;

import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.Post;
import com.edu.active.dao.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostsRepository postsRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable long id) {
        User user = usersRepository.findOne(id);
//        System.out.println(user.getId());
//        System.out.println(user.getLikedPosts());
//        System.out.println(user.getCreatedPosts());
        return user;
    }

    @RequestMapping(name = "/{id}/posts", method = RequestMethod.GET)
    public Set<Post> getUserPosts(@PathVariable long id) {
        User user = usersRepository.findOne(id);
        return user.getCreatedPosts();
    }


    @RequestMapping(method = RequestMethod.POST)
    public void saveUser(@RequestBody User user) {
        usersRepository.save(user);
    }


    @RequestMapping(method = RequestMethod.POST)
    public void savePost(@RequestBody Post post) {
        postsRepository.save(post);
    }


}
