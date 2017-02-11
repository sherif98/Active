package com.edu.active.controllers;

import com.edu.active.services.mail.EmailService;
import com.edu.active.services.storage.api.UserStorageService;
import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Image;
import com.edu.active.services.storage.model.Post;
import com.edu.active.services.storage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.edu.active.controllers.exceptions.GlobalExceptionHandlingController.invalidData;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    private EmailService emailService;

    @Autowired
    private UserStorageService userStorageService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<User> getUserById(@PathVariable long id) {

        return userStorageService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user " + id));
    }

    @RequestMapping(value = "/{userId}/image", method = RequestMethod.GET)
    public Resource<Image> getUserImage(@PathVariable long userId) {
        return userStorageService.getUserImage(userId)
                .orElseThrow(() -> new ResourceNotFoundException("image " + userId));

    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public Page<Resource<Post>> getUserPosts(
            @PathVariable long id,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return userStorageService.getUserPosts(id, pageable)
                .orElseThrow(() -> new ResourceNotFoundException("user " + id));
    }


    @RequestMapping(value = "/{id}/categories", method = RequestMethod.GET)
    public Page<Resource<Category>> categoriesFollowing(
            @PathVariable long id, @PageableDefault(size = 10, page = 0) Pageable pageable) {

        return userStorageService.getUserFollowingCategories(id, pageable)
                .orElseThrow(() -> new ResourceNotFoundException("user " + id));
    }

    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET)
    public Page<Resource<Post>> getLikedPosts(
            @PathVariable long id, @PageableDefault(size = 10, page = 0) Pageable pageable) {

        return userStorageService.getUserLikedPosts(id, pageable)
                .orElseThrow(() -> new ResourceNotFoundException("user " + id));
    }

    @RequestMapping(value = "/{userId}/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void savePost(@RequestBody @Valid Post post,
                         Errors errors, @PathVariable long userId, @RequestParam(name = "category") long categoryId) {

        userStorageService.addPostToUserCreatedPosts(userId, post, categoryId);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody @Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            invalidData(errors.getAllErrors());
        }
        userStorageService.registerNewUser(user);
        emailService.sendEmail(user);
    }


    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public void followCategory(@PathVariable long userId, @RequestParam("categoryId") long categoryId) {
        userStorageService.addCategoryToUserFollowingCategories(userId, categoryId);
    }

//    private boolean userExists(@RequestBody User user) {
//        return usersRepository.findUserByUserName(user.getUserName()) != null;
//    }
}
