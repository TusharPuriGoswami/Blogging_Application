package com.BlogApi.Payloads;

import java.sql.Date;
import java.util.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
		
	private List<CommentDto> comments = new ArrayList<>();
	
	
	
	
}
