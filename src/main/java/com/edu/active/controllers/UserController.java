package com.edu.active.controllers;

import com.edu.active.controllers.dto.Category;
import com.edu.active.controllers.dto.Image;
import com.edu.active.controllers.dto.Post;
import com.edu.active.controllers.dto.User;
import com.edu.active.controllers.exceptions.ResourceAlreadyExistsException;
import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.ImageEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import com.edu.active.services.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static com.edu.active.controllers.exceptions.GlobalExceptionHandlingController.*;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UsersRepository usersRepository;


    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<User> getUserById(@PathVariable long id) {
        UserEntity userEntity = usersRepository.findOne(id);
        if (userEntity == null) {
            userNotFound(id);
        }
        return User.getResource(userEntity);
    }

    @RequestMapping(value = "/{userId}/image", method = RequestMethod.GET)
    public Resource<Image> getUserImage(@PathVariable long userId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        ImageEntity imageEntity = userEntity.getImageEntity();
        Resource<Image> imageResource = Image.getResource(imageEntity);
        return imageResource;
    }

    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public Page<Resource<Post>> getUserPosts(
            @PathVariable long id,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        UserEntity userEntity = usersRepository.findOne(id);
        if (userEntity == null) {
            userNotFound(id);
        }
        Page<PostEntity> page = postsRepository.findPostsByOwnerUser(userEntity, pageable);
        return page.map(Post::getResource);
//        Set<Resource<Post>> posts = userEntity.getCreatedPosts().stream().map(Post::getResource).collect(Collectors.toSet());

//        List<Resource<Post>> resourceList = userEntity.getCreatedPosts().stream().map(Post::getResource).collect(Collectors.toList());
//        Page<Resource<Post>> page = new PageImpl<Resource<Post>>(resourceList, pageable, resourceList.size());
//        return page;
    }


    @RequestMapping(value = "/{id}/categories", method = RequestMethod.GET)
    public Page<Resource<Category>> categoriesFollowing(
            @PathVariable long id, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        UserEntity userEntity = usersRepository.findOne(id);
        if (userEntity == null) {
            userNotFound(id);
        }
        //TODO test this method
        Page<CategoryEntity> categoryEntityPage = categoriesRepository.findCategoriesByUsersFollowingCategoryContaining(userEntity, pageable);
        return categoryEntityPage.map(Category::getResource);

//        Set<CategoryEntity> categoryEntities = userEntity.getCategoriesFollowing();
//        Set<Resource<Category>> resourceCategories = categoryEntities.stream().map(Category::getResource).collect(Collectors.toSet());
//        return resourceCategories;
    }

    //TODO test this method
    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET)
    public Page<Resource<Post>> getLikedPosts(
            @PathVariable long id, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        UserEntity userEntity = usersRepository.findOne(id);
        if (userEntity == null) {
            userNotFound(id);
        }
        Page<PostEntity> postEntityPage = postsRepository.findPostsByUsersLikePostContaining(userEntity, pageable);
        return postEntityPage.map(Post::getResource);
//        Set<PostEntity> postEntities = userEntity.getLikedPosts();
//        Set<Resource<Post>> resourcePosts = postEntities.stream().map(Post::getResource).collect(Collectors.toSet());
//        return resourcePosts;
    }

    @RequestMapping(value = "/{userId}/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void savePost(@RequestBody @Valid Post post,
                         Errors errors, @PathVariable long userId, @RequestParam(name = "category") long categoryId) {

        if (errors.hasErrors()) {
            logger.info("errors exists in post" + errors);
            invalidData(errors.getAllErrors());
        }
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            userNotFound(userId);
        }
        CategoryEntity categoryEntity = categoriesRepository.findOne(categoryId);
        if (categoryEntity == null) {
            categoryNotFound(categoryId);
        }

        PostEntity postEntity = new PostEntity(post, userEntity, categoryEntity);
        userEntity.getCreatedPosts().add(postEntity);
        categoryEntity.getPosts().add(postEntity);

        usersRepository.save(userEntity);
        categoriesRepository.save(categoryEntity);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void saveUser(@RequestBody @Valid User user, Errors errors) {
        UserEntity userEntity = new UserEntity(user);
        if (errors.hasErrors()) {
            invalidData(errors.getAllErrors());
        }
        if (userExists(user)) {
            throw new ResourceAlreadyExistsException("user " + user.getUserName());
        }
        usersRepository.save(userEntity);
        emailService.sendEmail(user);
    }


    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public void followCategory(@PathVariable long userId, @RequestParam("categoryId") long categoryId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            userNotFound(userId);
        }
        CategoryEntity categoryEntity = categoriesRepository.findOne(categoryId);
        if (categoryEntity == null) {
            categoryNotFound(categoryId);
        }
        Set<CategoryEntity> categoryEntities = userEntity.getCategoriesFollowing();
        categoryEntities.add(categoryEntity);
        usersRepository.save(userEntity);
    }

    private boolean userExists(@RequestBody User user) {
        return usersRepository.findUserByUserName(user.getUserName()) != null;
    }
}
