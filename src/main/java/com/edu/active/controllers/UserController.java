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
import org.springframework.hateoas.Resource;
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
    public Resource<User> getUserById(@PathVariable long id) {
        UserEntity userEntity = usersRepository.findOne(id);
        return User.getResource(userEntity);
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public Set<Resource<Post>> getUserPosts(@PathVariable long id) {
        UserEntity user = usersRepository.findOne(id);
        Set<Resource<Post>> posts = user.getCreatedPosts().stream().map(Post::getResource).collect(Collectors.toSet());
        return posts;
    }

    @RequestMapping(value = "/{id}/categories", method = RequestMethod.GET)
    public Set<Resource<Category>> categoriesFollowing(@PathVariable long id) {
        UserEntity userEntity = usersRepository.findOne(id);
        Set<CategoryEntity> categoryEntities = userEntity.getCategoriesFollowing();
        Set<Resource<Category>> resourceCategories = categoryEntities.stream().map(Category::getResource).collect(Collectors.toSet());
        return resourceCategories;
    }

    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET)
    public Set<Resource<Post>> getLikedPosts(@PathVariable long id) {
        UserEntity userEntity = usersRepository.findOne(id);
        Set<PostEntity> postEntities = userEntity.getLikedPosts();
        Set<Resource<Post>> resourcePosts = postEntities.stream().map(Post::getResource).collect(Collectors.toSet());
        return resourcePosts;
    }

    @RequestMapping(value = "/{userId}/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void savePost(@RequestBody Post post, @PathVariable long userId, @RequestParam(name = "category") long categoryId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        CategoryEntity categoryEntity = categoriesRepository.findOne(categoryId);

        PostEntity postEntity = new PostEntity(post, userEntity, categoryEntity);
        userEntity.getCreatedPosts().add(postEntity);
        categoryEntity.getPosts().add(postEntity);

        usersRepository.save(userEntity);
        categoriesRepository.save(categoryEntity);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody User user) {
        UserEntity userEntity = new UserEntity(user);
        usersRepository.save(userEntity);
    }

    //TODO make user follow a category  (PUT)

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public void followCategory(@PathVariable long userId, @RequestParam("categoryId") long categoryId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        CategoryEntity categoryEntity = categoriesRepository.findOne(categoryId);
        Set<CategoryEntity> categoryEntities = userEntity.getCategoriesFollowing();
        categoryEntities.add(categoryEntity);
        usersRepository.save(userEntity);
    }
}
