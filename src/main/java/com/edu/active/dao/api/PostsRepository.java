package com.edu.active.dao.api;

import com.edu.active.dao.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PostsRepository extends JpaRepository<PostEntity, Long> {
}
