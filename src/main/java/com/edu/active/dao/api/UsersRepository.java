package com.edu.active.dao.api;

import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UsersRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserByUserName(String name);

    Page<UserEntity> findUsersByLikedPostsContaining(PostEntity post, Pageable pageable);
}
