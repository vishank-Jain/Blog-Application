package com.codewithvishank.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithvishank.blog.entities.Category;
import com.codewithvishank.blog.exceptions.ResourceNotFound;
import com.codewithvishank.blog.payloads.CategoryDto;
import com.codewithvishank.blog.repositries.CategoryRepo;
import com.codewithvishank.blog.service.CategoryService;
@Service
public class CategoryImp implements CategoryService{
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat=this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		Category cat=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFound("Category", "CategoryId", id));
		cat.setTitle(categoryDto.getTitle());
		cat.setDescription(categoryDto.getDescription());
		Category updateCategory=this.categoryRepo.save(cat);
		return this.modelMapper.map(updateCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer id) {
		Category cat=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFound("Category", "CategoryId", id));
		this.categoryRepo.delete(cat);
		
	}

	@Override
	public CategoryDto getCategory(Integer id) {
		Category cat=this.categoryRepo.findById(id).orElseThrow(()-> new ResourceNotFound("Category", "CategoryId", id));
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<Category> categories=this.categoryRepo.findAll();
		List<CategoryDto> categoryDto=categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return categoryDto;
	}
	
	}
	

