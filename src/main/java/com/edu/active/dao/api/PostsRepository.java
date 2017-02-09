package com.edu.active.dao.api;

import com.edu.active.dao.entities.CategoryEntity;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PostsRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findPostsByOwnerUser(UserEntity ownerUser, Pageable pageable);

    Page<PostEntity>findPostsByUsersLikePostContaining(UserEntity userEntity, Pageable pageable);

    Page<PostEntity> findPostsByCategory(CategoryEntity categoryEntity, Pageable pageable);
}
