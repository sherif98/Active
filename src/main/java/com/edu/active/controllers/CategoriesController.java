package com.edu.active.controllers;

import com.edu.active.controllers.dto.Category;
import com.edu.active.controllers.dto.Post;
import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.edu.active.controllers.exceptions.GlobalExceptionHandlingController.*;

@RestController
@RequestMapping(value = "/category")
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Resource<Category>> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoriesRepository.findAll();
        List<Resource<Category>> resourceCategories = categoryEntities.stream().map(Category::getResource).collect(Collectors.toList());
        return resourceCategories;
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
    public Set<Resource<Post>> getCategoryPostsById(@PathVariable long id) {
        CategoryEntity categoryEntity = categoriesRepository.findOne(id);
        if (categoryEntity == null) {
            categoryNotFound(id);
        }
        Set<PostEntity> postEntities = categoryEntity.getPosts();
        Set<Resource<Post>> resourcePosts = postEntities.stream().map(Post::getResource).collect(Collectors.toSet());
        return resourcePosts;
    }
}
