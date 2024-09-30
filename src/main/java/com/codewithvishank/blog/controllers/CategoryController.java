package com.codewithvishank.blog.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.codewithvishank.blog.payloads.ApiResponse;
import com.codewithvishank.blog.payloads.CategoryDto;
import com.codewithvishank.blog.service.CategoryService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.OK);
		}
	
	
	@PutMapping("/{c_id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto , @PathVariable Integer c_id){
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, c_id);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	}
	@DeleteMapping("/{c_id}")
	public ResponseEntity<ApiResponse> deleteCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer c_id){
		this.categoryService.deleteCategory(c_id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted!!",true),HttpStatus.OK);
	}
	@GetMapping("/{c_id}")
	public ResponseEntity<CategoryDto> getCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer c_id){
		CategoryDto category = this.categoryService.getCategory(c_id);
		return new ResponseEntity<CategoryDto>(category,HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategory(){
		List<CategoryDto> categories=this.categoryService.getCategories();
		return ResponseEntity.ok(categories);
	}
	
}
