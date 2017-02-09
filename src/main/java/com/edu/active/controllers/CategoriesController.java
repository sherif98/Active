package com.edu.active.controllers;

import com.edu.active.controllers.dto.Category;
import com.edu.active.controllers.dto.Post;
import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import static com.edu.active.controllers.exceptions.GlobalExceptionHandlingController.categoryNotFound;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private PostsRepository postsRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Resource<Category>> getAllCategories(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<CategoryEntity> categoryEntities = categoriesRepository.findAll(pageable);
        return categoryEntities.map(Category::getResource);
//        List<Resource<Category>> resourceCategories = categoryEntities.stream().map(Category::getResource).collect(Collectors.toList());
//        return resourceCategories;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<Category> getCategoryById(@PathVariable long id) {
        CategoryEntity categoryEntity = categoriesRepository.findOne(id);
        if (categoryEntity == null) {
            categoryNotFound(id);
        }
        Resource<Category> categoryResource = Category.getResource(categoryEntity);
        return categoryResource;
    }


    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public Page<Resource<Post>> getCategoryPostsById(
            @PathVariable long id, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        CategoryEntity categoryEntity = categoriesRepository.findOne(id);
        if (categoryEntity == null) {
            categoryNotFound(id);
        }
        Page<PostEntity> postEntityPage = postsRepository.findPostsByCategory(categoryEntity, pageable);
        return postEntityPage.map(Post::getResource);

//        Set<PostEntity> postEntities = categoryEntity.getPosts();
//        Set<Resource<Post>> resourcePosts = postEntities.stream().map(Post::getResource).collect(Collectors.toSet());
//        return resourcePosts;
    }
}
