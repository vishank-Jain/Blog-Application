package com.codewithvishank.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvishank.blog.payloads.ApiResponse;
import com.codewithvishank.blog.payloads.UserDto;
import com.codewithvishank.blog.service.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto){
		UserDto createUserDto=this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
		
	}
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId){
		UserDto updateUser=this.userService.updateUser(userDto,userId);
		return new ResponseEntity<UserDto>(updateUser,HttpStatus.OK);
	}
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable ("userId") Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getAllUsers() {
      return ResponseEntity.ok(this.userService.getAllUser());
  }
  
  
  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId ) {
      return ResponseEntity.ok(this.userService.getUser(userId));
  }
  
}