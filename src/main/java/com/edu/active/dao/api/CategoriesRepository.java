package com.edu.active.dao.api;

import com.edu.active.dao.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<CategoryEntity, Long> {

    public CategoryEntity findCategoryByCategoryName(String categoryName);
}
