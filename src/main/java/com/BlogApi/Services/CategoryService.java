package com.BlogApi.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.BlogApi.Payloads.CategoryDto;

public interface CategoryService {

	//create 
	 CategoryDto createCategoty(CategoryDto categoryDto);
	
	//update 
	 CategoryDto updateCategoty(CategoryDto categoryDto , Integer categoryId);

	//delete 
	 void deleteCategory(Integer categoryId);
	 
	//get single
	 CategoryDto getCategory(Integer categoryId);
	 
	//get all
	 List<CategoryDto> getCategories();
}
