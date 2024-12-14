package com.BlogApi.Services;

import org.springframework.stereotype.Service;

import com.BlogApi.Payloads.CommentDto;

@Service
public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto , Integer postId);
	void deleteComment(Integer commentId);
	CommentDto updateComment(CommentDto commentDto , Integer commentId);

}
