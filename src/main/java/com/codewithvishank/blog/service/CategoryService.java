package com.codewithvishank.blog.service;

import java.util.List;

import com.codewithvishank.blog.payloads.CategoryDto;

public interface CategoryService {
	
//	Create the category
	CategoryDto createCategory(CategoryDto categoryDto);
	
//	update the category
	CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
	
//	Delete category
	void deleteCategory(Integer id);
	
//	get
	CategoryDto getCategory(Integer id);
	
//	get all
	List<CategoryDto> getCategories();
	
	
}
