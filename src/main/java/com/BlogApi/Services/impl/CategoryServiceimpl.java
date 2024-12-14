package com.BlogApi.Services.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.BlogApi.Entites.Category;
import com.BlogApi.Exceptions.ResourceNotFoundException;
import com.BlogApi.Payloads.CategoryDto;
import com.BlogApi.Repositories.CategoryRepo;
import com.BlogApi.Services.CategoryService;

@Service
public class CategoryServiceimpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	
	//CREATE API
	@Override
	public CategoryDto createCategoty(CategoryDto categoryDto) {
		
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addCat = this.categoryRepo.save(cat);
		return this.modelMapper.map(addCat, CategoryDto.class);
	}

	//UPDATE API
	@Override
	public CategoryDto updateCategoty(CategoryDto categoryDto, Integer categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedcat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	//DELETE API
	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        this.categoryRepo.delete(cat);
		
	}

	//GET API
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		 Category cat = this.categoryRepo.findById(categoryId)
	                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
	        return this.modelMapper.map(cat, CategoryDto.class);
	}

	//GET API ALL 
	@Override
	public List<CategoryDto> getCategories() {
		
		 List<Category> categories = this.categoryRepo.findAll();
	        return categories.stream()
	                .map(cat -> this.modelMapper.map(cat, CategoryDto.class))
	                .collect(Collectors.toList());
	    
		
	}

}
