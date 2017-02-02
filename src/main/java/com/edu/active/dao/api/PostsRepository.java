package com.edu.active.dao.api;

import com.edu.active.dao.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Post, Long> {
}
