package com.codewithvishank.blog.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithvishank.blog.config.AppConstants;
import com.codewithvishank.blog.payloads.ApiResponse;
import com.codewithvishank.blog.payloads.PostDto;
import com.codewithvishank.blog.payloads.PostResponse;
import com.codewithvishank.blog.service.FileService;
import com.codewithvishank.blog.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class PostController {
	
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
//create	
@PostMapping("/user/{userid}/category/{categoryid}/posts/")
public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userid ,@PathVariable Integer categoryid) {
    PostDto createpost = this.postService.createPost(postDto, userid, categoryid);
    
    return new ResponseEntity<PostDto> (createpost,HttpStatus.CREATED);
}

// Get post by category
@GetMapping("/category/{categoryid}/posts/")
public ResponseEntity<List<PostDto>> getCategoryByCategoryId(@PathVariable Integer categoryid) {
	List<PostDto> postByCategory = this.postService.getPostByCategory(categoryid);
    return new ResponseEntity<List<PostDto>>(postByCategory,HttpStatus.OK);
}

// Get post by User
@GetMapping("/user/{userid}/posts/")
public ResponseEntity<List<PostDto>> getCategoryByUserId(@PathVariable Integer userid){
	List<PostDto> postByUser=this.postService.getPostByUser(userid);
	return new ResponseEntity<List<PostDto>>(postByUser,HttpStatus.OK);
	}

// Get post by postid
@GetMapping("/post/{pId}/posts/")
public ResponseEntity<PostDto> getPostByPid(@PathVariable Integer pId){
	PostDto postById = this.postService.getPostById(pId);
	return new ResponseEntity<PostDto> (postById,HttpStatus.OK);
	
}

// Delete post by pid
@DeleteMapping("/post/{pId}")
public ApiResponse deleteByPid(@PathVariable Integer pId) {
	this.postService.delete(pId);
	return new ApiResponse("Post is successfully deleted!!",true);	
}

//Update post
@PutMapping("/update/{pId}")
public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer pId){
	PostDto result = this.postService.updatePost(postDto, pId);
	return new ResponseEntity<PostDto>(result,HttpStatus.OK);
}

// Search By keyWord
@GetMapping("/posts/search/{keyword}")
public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keywords){
	List<PostDto> result=this.postService.searchPost(keywords);
	return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
}

@GetMapping("/posts")
public ResponseEntity<PostResponse> getAllPosts(@RequestParam (value="pageNumber" ,defaultValue=AppConstants.PAGE_NUMBER ,required=false)Integer pageNumber,
		@RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
		@RequestParam(value="sortby",defaultValue = AppConstants.SORT_BY,required = false)String sortby,
		@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
	PostResponse result=this.postService.getAllPost(pageNumber,pageSize,sortby,sortDir);
	return new ResponseEntity<PostResponse>(result,HttpStatus.OK);
}
@Value("${project.image}")
private String path;


@PostMapping("/post/image/upload/{postId}")
public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
		@PathVariable Integer postId) throws IOException {

	PostDto postDto = this.postService.getPostById(postId);
	
	String fileName = this.fileService.uploadImage(path, image);
	postDto.setImageName(fileName);
	PostDto updatePost = this.postService.updatePost(postDto, postId);
	return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
}
@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
) throws IOException {

    InputStream resource = this.fileService.getResources(path, imageName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource,response.getOutputStream())   ;

}
}
