package com.BlogApi.Repositories;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BlogApi.Entites.Category;
import com.BlogApi.Entites.Post;
import com.BlogApi.Entites.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostRepo extends JpaRepository<Post, Integer>{

//    @Modifying
//    @Transactional
//    @Query("DELETE FROM Post p WHERE p.id = :postId")
//    void deletePostById(Integer postId);



    Page<Post> findByUser(User user , Pageable pageable);

Page<Post> findByCategory(Category category , Pageable pageable);

List<Post> findByTitleContaining(String title);



}
