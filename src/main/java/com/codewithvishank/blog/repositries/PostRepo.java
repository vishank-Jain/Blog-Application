package com.codewithvishank.blog.repositries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codewithvishank.blog.entities.Category;
import com.codewithvishank.blog.entities.Posts;
import com.codewithvishank.blog.entities.User;

import java.util.List;


public interface PostRepo extends JpaRepository<Posts,Integer>{
	List<Posts> findByUser(User user);
	List<Posts> findByCategory(Category category);
	@Query("select p from Posts p where p.title like :key")
	List<Posts> searchByTitle(@Param("key") String title);
}
