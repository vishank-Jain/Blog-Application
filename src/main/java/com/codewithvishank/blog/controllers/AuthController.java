package com.codewithvishank.blog.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codewithvishank.blog.entities.User;
import com.codewithvishank.blog.exceptions.ApiException;
import com.codewithvishank.blog.payloads.JwtRequest;
import com.codewithvishank.blog.payloads.JwtResponse;
import com.codewithvishank.blog.payloads.UserDto;
import com.codewithvishank.blog.security.CustomerUserDetailService;
import com.codewithvishank.blog.security.JwtTokenHelper;
import com.codewithvishank.blog.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private CustomerUserDetailService customerUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> createToken(@RequestBody JwtRequest request) throws Exception{
//		authenticate the username and password
		this.authenticate(request.getUsername(),request.getPassword()); 
		
//		Load username by email ,this customize finder created in CustomerUserDetailService
		UserDetails userDetails = this.customerUserDetailsService.loadUserByUsername(request.getUsername());
		
//		now we generate the token by JwttokenHelper class
		String token = this.jwtTokenHelper.generateToken(userDetails);
//		Set the response 
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setToken(token);
		jwtResponse.setUser(this.modelMapper.map((User) userDetails,UserDto.class));
		
//		return the status
		return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
		
	}
	
	
	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);
		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}
	

	// register new user api

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser( @RequestBody UserDto userDto) {
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
	}
}
