package com.edu.active.controllers;

import com.edu.active.services.storage.api.CategoryStorageService;
import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin
public class CategoriesController {


    @Autowired
    private CategoryStorageService categoryStorageService;


    @RequestMapping(method = RequestMethod.GET)
    public Page<Resource<Category>> getAllCategories(@PageableDefault Pageable pageable) {

        return categoryStorageService.getAllCategories(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<Category> getCategoryById(@PathVariable long id) {
        return categoryStorageService.getCategoryById(id);
    }


    @RequestMapping(value = "/{id}/posts", method = RequestMethod.GET)
    public Page<Resource<Post>> getCategoryPostsById(
            @PathVariable long id, @PageableDefault Pageable pageable) {

        return categoryStorageService.getPostsUnderCategory(id, pageable);
    }
}
