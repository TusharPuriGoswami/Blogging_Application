package com.BlogApi.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BlogApi.Entites.Post;
import com.BlogApi.Payloads.PostDto;
import com.BlogApi.Payloads.PostResponse;


public interface PostService {
	
	//CREATE POST
	PostDto createPost(PostDto postDto,Integer	userId,Integer categoryId);
	
	//UPDATE POST
	PostDto updatePost(PostDto postDto , Integer postId);
	
	//DELETE POST
	void deletePost(Integer postId);
	
	//GET ALL POST
	PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy ,String sortDir);
	
	//GET SINGLE POST
	PostDto getPostById(Integer postId);
	
	//GET ALL POST BY CATEGORY
	PostResponse getPostByCategory(Integer categoryId , Integer pageNumber, Integer pageSize);
	
	//GET ALL POST BY USER
	PostResponse getPostByUser(Integer userId  , Integer pageNumber, Integer pageSize);
	
	//SEARCH POST
	List<PostDto> searchPost(String keyword);

	

	
	
}
