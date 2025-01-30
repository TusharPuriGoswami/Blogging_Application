package com.BlogApi.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.BlogApi.Payloads.PostDto;
import com.BlogApi.Payloads.PostResponse;
import com.BlogApi.Services.FileService;
import com.BlogApi.Services.PostService;
import com.BlogApi.config.AppConstants;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
    private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	//Create Api
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId)
	{
	PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
	return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
			
	} 
	
	 // Get posts by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(
	        @PathVariable Integer userId,
	        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
	        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
	    
	    PostResponse postResponse = this.postService.getPostByUser(userId, pageNumber, pageSize);
	    return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	
	  // Get posts by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getPostsByCategory(
	        @PathVariable Integer categoryId,
	        @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
	        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
	    
	    PostResponse postResponse = this.postService.getPostByCategory(categoryId, pageNumber, pageSize);
	    return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
    
 // GET All Posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
    		@RequestParam(value=  "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
    		@RequestParam(value = "pageSize"   , defaultValue = AppConstants.PAGE_SIZE  , required = false)Integer pageSize , 
    		@RequestParam(value = "sortBy" ,     defaultValue = AppConstants.SOET_BY , required = false) String sortBy,
    		@RequestParam(value = "sortDir" ,    defaultValue = AppConstants.SORT_DIR , required = false) String sortDir){
    	
    	
        PostResponse postResponse = this.postService.getAllPost(pageNumber , pageSize , sortBy , sortDir);
        return new ResponseEntity<PostResponse>(postResponse , HttpStatus.OK);
        		// Returns 200 OK with list of PostDto
    }

    // GET Single Post by ID
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto post = postService.getPostById(postId); // Fetching as PostDto
        return new ResponseEntity<>(post, HttpStatus.OK); // Returns 200 OK with PostDto
    }
    
    
    //delete post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Integer postId){
    	
    	this.postService.deletePost(postId);
    	return ResponseEntity.ok("post deleted Successfully");
    }
    
    //update post 
    
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto , @PathVariable Integer postId){
    
    	PostDto updatePost = this.postService.updatePost(postDto , postId);
    	return ResponseEntity.ok(updatePost);
    		
    }
    
    //Search Controller API
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
    		@PathVariable("keywords") String keywords){
    	
    	List<PostDto> result = this.postService.searchPost(keywords);
    	return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
    }
    
    //Post Image upload Controller
    
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<?> uploadPostImage(@RequestParam("image") MultipartFile image,
                                             @PathVariable Integer postId) throws IOException {

        // Validate file type
        String contentType = image.getContentType();
        if (contentType == null || 
           (!contentType.equals("image/jpeg") && !contentType.equals("image/png"))) {
            return new ResponseEntity<>("Invalid file type. Only JPG and PNG files are allowed.", HttpStatus.BAD_REQUEST);
        }

        // Validate file size (optional, example: 2MB limit)
        if (image.getSize() > 2 * 1024 * 1024) {
            return new ResponseEntity<>("File size exceeds limit (2MB).", HttpStatus.BAD_REQUEST);
        }

        // Fetch the post
        PostDto postDto = this.postService.getPostById(postId);

        // Upload the image
        String fileName = this.fileService.uploadImage(path, image);

        // Update post with image name
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    
//    @PostMapping("/post/image/upload/{postId}")
//    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
//    		@PathVariable Integer postId)throws IOException{
//    	
//    		
//    	PostDto postDto = this.postService.getPostById(postId);
//    	String fileName = this.fileService.uploadImage(path, image);
//    	
//    	postDto.setImageName(fileName);
//    	PostDto updatePost = this.postService.updatePost(postDto, postId);
//    	return new ResponseEntity<PostDto>(updatePost , HttpStatus.OK);
//    }
//    
    
    
    
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }


   
}
