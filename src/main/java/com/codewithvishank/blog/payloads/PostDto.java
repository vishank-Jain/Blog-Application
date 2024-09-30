package com.codewithvishank.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.codewithvishank.blog.entities.Category;
import com.codewithvishank.blog.entities.Comment;
import com.codewithvishank.blog.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	
	private String content;
	private String title;
	private String imageName;
	private Date date;
	
	private Category category;
	
	private User user;
	private Set<Comment> comments=new HashSet<>();
	
	
}
