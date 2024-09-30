package com.codewithvishank.blog.repositries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvishank.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
