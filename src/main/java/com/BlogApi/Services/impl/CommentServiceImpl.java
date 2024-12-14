package com.BlogApi.Services.impl;

import com.BlogApi.Entites.Comment;
import com.BlogApi.Entites.Post;
import com.BlogApi.Exceptions.ResourceNotFoundException;
import com.BlogApi.Payloads.CommentDto;
import com.BlogApi.Repositories.CommentRepo;
import com.BlogApi.Repositories.PostRepo;
import com.BlogApi.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        // Find the post by ID
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

        // Convert CommentDto to Comment entity
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setPost(post); // Link the comment to the post

        // Save the comment to the database
        Comment savedComment = commentRepo.save(comment);

        // Convert saved Comment entity to CommentDto
        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        commentRepo.delete(comment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        // Find the existing comment
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));

        // Update the comment content
        comment.setContent(commentDto.getContent());

        // Save the updated comment
        Comment updatedComment = commentRepo.save(comment);

        // Convert updated Comment entity to CommentDto
        return modelMapper.map(updatedComment, CommentDto.class);
    }
}
