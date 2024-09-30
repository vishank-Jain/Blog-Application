package com.codewithvishank.blog.service;

import java.util.List;

import com.codewithvishank.blog.payloads.PostDto;
import com.codewithvishank.blog.payloads.PostResponse;

public interface PostService {

		PostDto createPost(PostDto postDto,Integer userid,Integer categoryId);
		
		PostDto updatePost(PostDto postDto,Integer postId);
		
		void delete(Integer postId);
		
		PostDto getPostById(Integer pId);
		
		PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy ,String sortDir);
		
		List<PostDto> getPostByCategory(Integer categoryId);
		
		List<PostDto> getPostByUser(Integer Uid);
		
		List<PostDto> searchPost(String keyword);
		
		
		
		
}
