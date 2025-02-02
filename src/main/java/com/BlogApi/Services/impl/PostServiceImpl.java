package com.BlogApi.Services.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.BlogApi.Entites.Category;
import com.BlogApi.Entites.Post;
import com.BlogApi.Entites.User;
import com.BlogApi.Exceptions.ResourceNotFoundException;
import com.BlogApi.Payloads.CommentDto;
import com.BlogApi.Payloads.PostDto;
import com.BlogApi.Payloads.PostResponse;
import com.BlogApi.Repositories.CategoryRepo;
import com.BlogApi.Repositories.PostRepo;
import com.BlogApi.Repositories.UserRepo;
import com.BlogApi.Services.PostService;

@Service
public class PostServiceImpl implements PostService{

	//private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);




	@Autowired
    private PostRepo postRepo;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId).orElseThrow(()
				->new ResourceNotFoundException("User", "user Id", userId));
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()
				->new ResourceNotFoundException("Category", "category Id", categoryId));
		
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date(System.currentTimeMillis()));
		post.setUser(user);
		post.setCategory(category);
		
	    Post newpost = this.postRepo.save(post);
		
		return this.modelMapper.map(newpost, PostDto.class);
	}

	//update post
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
	    
	    Post post = this.postRepo.findById(postId)
	            .orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
	    
	    // Update the post
	    
	   Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
	    
	    
	    post.setTitle(postDto.getTitle());
	    post.setContent(postDto.getContent());
	    post.setImageName(postDto.getImageName());
	    post.setCategory(category);


	    Post updatedPost = this.postRepo.save(post);
	    
	    return this.modelMapper.map(updatedPost, PostDto.class);
	}

	//delete post
	
	@Transactional
	@Override
	public void deletePost(Integer postId) {
	    Post post = this.postRepo.findById(postId)
	            .orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
	    this.postRepo.delete(post);
	}
//	@Override
//	public void deletePost(Integer postId) {
//		//find the post id
//
//		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","post Id", postId));
//
//		this.postRepo.delete(post);
//	}



	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy , String sortDir) {
	    // Validate sortBy field
		/*
		 * List<String> allowedSortFields = Arrays.asList("postId", "title",
		 * "addedDate"); if (!allowedSortFields.contains(sortBy)) { throw new
		 * IllegalArgumentException("Invalid sortBy parameter: " + sortBy); }
		 */
		
		//Ternary Operator
		Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());

		
		
//		if(sortDir.equalsIgnoreCase("asc")) {
//			
//			sort = Sort.by(sortBy).ascending();
//		}
//		else{
//			sort = Sort.by(sortBy).descending();
//		}
		
		
	    // Create Pageable with descending sort
	    Pageable p = PageRequest.of(pageNumber, pageSize, sort);
	

	    // Fetch paginated posts
	    Page<Post> pagePost = this.postRepo.findAll(p);

	    // Map posts to PostDto
	    List<PostDto> postDtos = pagePost.getContent()
	            .stream()
	            .map(post -> this.modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());

	    // Create PostResponse
	    PostResponse postResponse = new PostResponse();
	    postResponse.setContent(postDtos);
	    postResponse.setPageNumber(pagePost.getNumber());
	    postResponse.setPageSize(pagePost.getSize());
	    postResponse.setTotalElements(pagePost.getTotalElements());
	    postResponse.setTotalPages(pagePost.getTotalPages());
	    postResponse.setLastPages(pagePost.isLast());

	    return postResponse;
	}

	
	@Override
	public PostDto getPostById(Integer postId) {
	    Post post = this.postRepo.findById(postId)
	            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

	    PostDto postDto = this.modelMapper.map(post, PostDto.class);

	    // Map comments to DTOs
	    List<CommentDto> commentDtos = post.getComments().stream()
	            .map(comment -> this.modelMapper.map(comment, CommentDto.class))
	            .collect(Collectors.toList());

	    postDto.setComments(commentDtos);
	    return postDto;
	}






	@Override
	public PostResponse getPostByCategory(Integer categoryId, Integer pageNumber, Integer pageSize) {
	    // Find the category first
	    Category cat = this.categoryRepo.findById(categoryId)
	            .orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));
	    
	    // Use a custom repository method to fetch posts by category with pagination
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    Page<Post> pagePost = this.postRepo.findByCategory(cat, pageable);
	    
	    // Map the posts to PostDto
	    List<PostDto> postDtos = pagePost.getContent()
	            .stream()
	            .map(post -> this.modelMapper.map(post, PostDto.class))
	            .collect(Collectors.toList());
	    
	    
	    
	    
	    // Create the PostResponse object
	    PostResponse postResponse = new PostResponse();
	    postResponse.setContent(postDtos);
	    postResponse.setPageNumber(pagePost.getNumber());
	    postResponse.setPageSize(pagePost.getSize());
	    postResponse.setTotalElements(pagePost.getTotalElements());
	    postResponse.setTotalPages(pagePost.getTotalPages());
	    postResponse.setLastPages(pagePost.isLast());
	    
	    return postResponse;
	}

	

	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
	    User user = userRepo.findById(userId)
	                        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    Page<Post> postsPage = postRepo.findByUser(user, pageable);

	    List<PostDto> posts = postsPage.getContent()
	                                   .stream()
	                                   .map(post -> modelMapper.map(post, PostDto.class))
	                                   .toList();

	    PostResponse postResponse = new PostResponse();
	    postResponse.setContent(posts);
	    postResponse.setPageNumber(postsPage.getNumber());
	    postResponse.setPageSize(postsPage.getSize());
	    postResponse.setTotalElements(postsPage.getTotalElements());
	    postResponse.setTotalPages(postsPage.getTotalPages());
	    postResponse.setLastPages(postsPage.isLast());
	    return postResponse;
	}


	@Override
	public List<PostDto> searchPost(String keyword) {
		
		
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos =  posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	
    
  

}
