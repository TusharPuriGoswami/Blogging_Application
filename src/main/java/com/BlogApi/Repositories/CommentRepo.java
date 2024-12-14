package com.BlogApi.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.BlogApi.Entites.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
