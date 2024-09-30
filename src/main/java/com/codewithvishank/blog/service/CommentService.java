package com.codewithvishank.blog.service;

import com.codewithvishank.blog.payloads.CommentDto;

public interface CommentService  {
	CommentDto createComment(CommentDto commentDto, Integer postId);

	void deleteComment(Integer commentId);
}
