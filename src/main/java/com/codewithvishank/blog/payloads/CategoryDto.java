package com.codewithvishank.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	private int c_id;
	
	@NotBlank
	private String title;
	@NotBlank
	private String description;
	
	
	
}
