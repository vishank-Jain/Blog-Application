package com.codewithvishank.blog.repositries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvishank.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
