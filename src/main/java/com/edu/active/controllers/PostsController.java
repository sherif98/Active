package com.edu.active.controllers;

import com.edu.active.controllers.dto.Category;
import com.edu.active.controllers.dto.Post;
import com.edu.active.controllers.dto.User;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

import static com.edu.active.controllers.exceptions.GlobalExceptionHandlingController.*;

@RestController
@RequestMapping(value = "/post")
@CrossOrigin
public class PostsController {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<Post> getPost(@PathVariable long id) {
        PostEntity postEntity = postsRepository.findOne(id);
        if (postEntity == null) {
            postNotFound(id);
        }
        Resource<Post> postResource = Post.getResource(postEntity);
        return postResource;
    }

    @RequestMapping(value = "/{id}/category", method = RequestMethod.GET)
    public Resource<Category> getPostCategory(@PathVariable long id) {
        PostEntity postEntity = postsRepository.findOne(id);
        if (postEntity == null) {
            postNotFound(id);
        }
        CategoryEntity categoryEntity = postEntity.getCategory();
        Resource<Category> categoryResource = Category.getResource(categoryEntity);
        return categoryResource;
    }

    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET)
    public Set<Resource<User>> getUsersLikePost(@PathVariable long id) {
        PostEntity postEntity = postsRepository.findOne(id);
        if (postEntity == null) {
            postNotFound(id);
        }
        Set<UserEntity> userEntities = postEntity.getUsersLikePost();
        Set<Resource<User>> userResources = userEntities.stream().map(User::getResource).collect(Collectors.toSet());
        return userResources;
    }

    @RequestMapping(value = "/{postId}/likes", method = RequestMethod.PUT)
    public void likePost(@PathVariable long postId, @RequestParam(name = "userId") long userId) {
        UserEntity userEntity = usersRepository.findOne(userId);
        if (userEntity == null) {
            userNotFound(userId);
        }
        PostEntity postEntity = postsRepository.findOne(postId);
        if (postEntity == null) {
            postNotFound(postId);
        }
        userEntity.getLikedPosts().add(postEntity);
        usersRepository.save(userEntity);
        //TODO
        //check if added correctly ?
    }
}
