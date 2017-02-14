package com.edu.active.services.storage.impl;

import com.edu.active.controllers.exceptions.GlobalExceptionHandlingController;
import com.edu.active.dao.api.CategoriesRepository;
import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.services.storage.api.CategoryStorageService;
import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Service;

@Service
public class CategoryStorageServiceImpl implements CategoryStorageService {

    @Autowired
    private CategoriesRepository categoriesRepository;


    @Autowired
    private PostsRepository postsRepository;

    @Override
    public Page<Resource<Post>> getPostsUnderCategory(long categoryId, Pageable pageable) {
        CategoryEntity categoryEntity = categoriesRepository.findOne(categoryId);
        if (categoryEntity == null) {
            GlobalExceptionHandlingController.categoryNotFound(categoryId);
        }
        Page<PostEntity> postEntityPage = postsRepository.findPostsByCategory(categoryEntity, pageable);
        return postEntityPage.map(Post::getResource);
    }

    @Override
    public Page<Resource<Category>> getAllCategories(Pageable pageable) {
        Page<CategoryEntity> categoryEntities = categoriesRepository.findAll(pageable);
        return categoryEntities.map(Category::getResource);
    }

    @Override
    public Resource<Category> getCategoryById(long id) {
        CategoryEntity categoryEntity = categoriesRepository.findOne(id);
        if (categoryEntity == null) {
            GlobalExceptionHandlingController.categoryNotFound(id);
        }
        Resource<Category> categoryResource = Category.getResource(categoryEntity);
        return categoryResource;
    }
}
