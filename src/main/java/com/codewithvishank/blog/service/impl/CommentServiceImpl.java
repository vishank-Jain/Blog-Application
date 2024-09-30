package com.codewithvishank.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithvishank.blog.entities.Comment;
import com.codewithvishank.blog.entities.Posts;
import com.codewithvishank.blog.exceptions.ResourceNotFound;
import com.codewithvishank.blog.payloads.CommentDto;
import com.codewithvishank.blog.repositries.CommentRepo;
import com.codewithvishank.blog.repositries.PostRepo;
import com.codewithvishank.blog.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Posts posts=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFound("Post", "PostId", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPosts(posts);
		Comment saveComment = this.commentRepo.save(comment);
		return this.modelMapper.map(saveComment, CommentDto.class);

	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFound("Comment", "CommentId", commentId));
		this.commentRepo.delete(com);
		
	}

}
