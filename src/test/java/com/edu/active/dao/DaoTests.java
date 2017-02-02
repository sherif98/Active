package com.edu.active.dao;

import com.edu.active.dao.api.PostsRepository;
import com.edu.active.dao.api.UsersRepository;
import com.edu.active.dao.entities.PostEntity;
import com.edu.active.dao.entities.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DaoTests {
    //    @Autowired
//    private CategoriesRepository categoriesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PostsRepository postsRepository;

    //    @Test
//    public void test1() {
//        UserEntity user = new UserEntity();
//        user.setUserName("sherif");
//        user.setPassword("12345");
//        user.setLikedPosts(new HashSet<>());
//
//        CategoryEntity category = new CategoryEntity();
//        PostEntity post = createPost(user, category);
//
//        category.setCategoryName("Programming");
//        category.setPosts(new HashSet<>(Arrays.asList(post)));
//
//
//        user.setCategoriesFollowing(new HashSet<>(Arrays.asList(category)));
//        user.setCreatedPosts(new HashSet<>(Arrays.asList(post)));
//
//
//        usersRepository.save(user);
//    }
//
//    private PostEntity createPost(UserEntity user, CategoryEntity category) {
//        PostEntity post = new PostEntity();
//        post.setTitle("java");
//        post.setContent("java is great");
//        post.setOwnerUser(user);
//        post.setCategory(category);
//        return post;
//    }
    @Test
    public void test2() {
        UserEntity user = new UserEntity();
        user.setUserName("sherif");
        user.setPassword("12345");

        PostEntity post = new PostEntity();
        post.setTitle("java");
        post.setContent("java is great");
        post.setOwnerUser(user);
        post.setUsersLikePost(new HashSet<>(Arrays.asList(user)));

        user.setCreatedPosts(new HashSet<>(Arrays.asList(post)));
        user.setLikedPosts(new HashSet<>(Arrays.asList(post)));
        usersRepository.save(user);
    }
}
