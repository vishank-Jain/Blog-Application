package com.codewithvishank.blog.repositries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvishank.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	Optional<User> findByEmail(String email);
}
