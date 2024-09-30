package com.codewithvishank.blog.service.impl;





import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithvishank.blog.entities.Category;
import com.codewithvishank.blog.entities.Posts;
import com.codewithvishank.blog.entities.User;
import com.codewithvishank.blog.payloads.PostDto;
import com.codewithvishank.blog.payloads.PostResponse;
import com.codewithvishank.blog.repositries.CategoryRepo;
import com.codewithvishank.blog.repositries.PostRepo;
import com.codewithvishank.blog.repositries.UserRepo;
import com.codewithvishank.blog.service.PostService;
import com.codewithvishank.blog.exceptions.ResourceNotFound;

@Service
public class PostImpl implements PostService {
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userid, Integer categoryId) {
		
		  User user = this.userRepo.findById(userid).orElseThrow(()-> new ResourceNotFound("user", "Userid", userid));

	        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category", "category id ", categoryId));
		
		
		Posts post=this.modelMapper.map(postDto,Posts.class);
		post.setImageName("Default.png");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Posts newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Posts post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFound("post","postid", postId));
		Category category = this.categoryRepo.findById(postDto.getCategory().getC_id()).get();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCategory(category);
        Posts updatedPost=this.postRepo.save(post);
		
	
		return this.modelMapper.map(updatedPost, PostDto.class) ;
	}

	@Override
	public void delete(Integer postId) {
		Posts post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFound("post","postid", postId));
		this.postRepo.delete(post);
		
		
	}

	@Override
	public PostDto getPostById(Integer pId) {
		Posts post = this.postRepo.findById(pId).orElseThrow(()->new ResourceNotFound("Posts", "postid", pId));
		PostDto dto = this.modelMapper.map(post, PostDto.class);
		return dto;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortby,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortby).ascending();
		}
		else {
			sort=Sort.by(sortby).descending();
		}
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Posts> page = this.postRepo.findAll(p);
		List<Posts> all = page.getContent();
		
		
		List<PostDto> posts=all.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		
		
		postResponse.setContent(posts);
		postResponse.setPageNumber(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLastPage(page.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFound("Category","categoryid", categoryId));
		
		List<Posts> posts = this.postRepo.findByCategory(cat);
		
		List<PostDto> collect = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return collect;
	}

	@Override
	public List<PostDto> getPostByUser(Integer Uid) {
		User user = this.userRepo.findById(Uid).orElseThrow(()->new ResourceNotFound("user", "userid", Uid));
		
		List<Posts> posts=this.postRepo.findByUser(user);
		List<PostDto> collect = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Posts> posts = this.postRepo.searchByTitle("%" + keyword + "%");
		List<PostDto> dto= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return dto;
	}

}
