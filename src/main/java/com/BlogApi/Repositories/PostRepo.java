package com.BlogApi.Repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.BlogApi.Entites.Category;
import com.BlogApi.Entites.Post;
import com.BlogApi.Entites.User;


public interface PostRepo extends JpaRepository<Post, Integer>{
	
Page<Post> findByUser(User user , Pageable pageable);

Page<Post> findByCategory(Category category , Pageable pageable);

List<Post> findByTitleContaining(String title);


}
