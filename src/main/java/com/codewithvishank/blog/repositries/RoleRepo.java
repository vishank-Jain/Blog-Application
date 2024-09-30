package com.codewithvishank.blog.repositries;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvishank.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
