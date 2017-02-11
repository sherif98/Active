package com.edu.active.services.storage.api;

import com.edu.active.services.storage.model.Category;
import com.edu.active.services.storage.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resource;

import java.util.Optional;
//TODO add documentation
public interface CategoryStorageService {

    Optional<Page<Resource<Category>>> getAllCategories(Pageable pageable);

    Optional<Resource<Category>> getCategoryById(long id);

    Optional<Page<Resource<Post>>> getPostsUnderCategory(long categoryId, Pageable pageable);

}
