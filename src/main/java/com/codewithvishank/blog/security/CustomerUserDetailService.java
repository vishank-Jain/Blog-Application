package com.codewithvishank.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithvishank.blog.entities.User;
import com.codewithvishank.blog.exceptions.ResourceNotFound;
import com.codewithvishank.blog.repositries.UserRepo;

@Service
public class CustomerUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFound("User", "Email Id: ",username ));
		return user;
	}

}
