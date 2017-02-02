package com.edu.active.controllers;

import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.entities.Category;
import com.edu.active.dao.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(name = "/category")
public class CategoriesController {

    @Autowired
    private CategoriesRepository categoriesRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @RequestMapping(name = "/{categoryName}", method = RequestMethod.GET)
    public Set<Post> getCategoryPosts(@PathVariable String categoryName) {
        return categoriesRepository.findCategoryByCategoryName(categoryName).getPosts();
    }
}
