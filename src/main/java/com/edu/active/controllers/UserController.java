package com.edu.active.controllers;

import com.edu.active.controllers.dto.Category;
import com.edu.active.controllers.dto.Post;
import com.edu.active.controllers.dto.User;
import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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
        UserEntity userEntity = usersRepository.findOne(id);
        User user = new User(userEntity);
        return user;
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public Set<Post> getUserPosts(@PathVariable long id) {
        UserEntity user = usersRepository.findOne(id);
        Set<Post> posts = user.getCreatedPosts().stream().map(Post::new).collect(Collectors.toSet());
        return posts;
    }

    @RequestMapping(value = "/{id}categories", method = RequestMethod.GET)
    public Set<Category> categoriesFollowing(@PathVariable long id) {
        Set<CategoryEntity> categoryEntities = usersRepository.findOne(id).getCategoriesFollowing();
        Set<Category> categories = categoryEntities.stream().map(Category::new).collect(Collectors.toSet());
        return categories;
    }

    @RequestMapping(value = "/{userId}/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void savePost(@RequestBody PostEntity post, @PathVariable long userId, @RequestParam(name = "category") long categoryId) {
        CategoryEntity category = categoriesRepository.findOne(categoryId);
        post.setCategory(category);
        UserEntity user = usersRepository.findOne(userId);
        post.setOwnerUser(user);
        postsRepository.save(post);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody UserEntity user) {
        usersRepository.save(user);
    }


}
