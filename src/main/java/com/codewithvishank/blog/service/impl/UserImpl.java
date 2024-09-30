package com.codewithvishank.blog.service.impl;
import com.codewithvishank.blog.exceptions.*;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithvishank.blog.config.AppConstants;
import com.codewithvishank.blog.entities.Role;
import com.codewithvishank.blog.entities.User;
import com.codewithvishank.blog.payloads.UserDto;
import com.codewithvishank.blog.repositries.RoleRepo;
import com.codewithvishank.blog.repositries.UserRepo;
import com.codewithvishank.blog.service.UserService;

@Service 
public class UserImpl implements UserService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		// TODO Auto-generated method stub
		return this.UserToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		
		User user=this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFound("user","id",id));
		
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		
		User updatedUser=this.userRepo.save(user);
		UserDto userDto1= this.UserToUserDto(updatedUser);
		return userDto1;
	}

	@Override
	public UserDto getUser(Integer id) {
		User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFound("user", "id", id));
		return this.UserToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> users=this.userRepo.findAll();
		List<UserDto> allUsers=users.stream().map(u->this.UserToUserDto(u)).collect(Collectors.toList());
		return allUsers;
	}

	@Override
	public void deleteUser(Integer id) {
		User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFound("user","id",id));
		this.userRepo.delete(user);
		
	}
	private User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
//		user.setU_Id(userDto.getId());
//		user.setU_Name(userDto.getName());
//		user.setU_About(userDto.getAbout());
//		user.setU_Email(userDto.getEmail());
//		user.setU_Password(userDto.getPassword());
		return user;
		
	}
	private UserDto UserToUserDto(User user) {
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
//		userDto.setId(user.getU_Id());
//		userDto.setName(user.getU_Name());
//		userDto.setAbout(user.getU_About());
//		userDto.setEmail(user.getU_Email());
//		userDto.setPassword(user.getU_Password());
		return userDto;
	}
	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
//		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
//
//		user.getRole().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}

//	@Override
//	public UserDto registerNewUser(UserDto user) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	}

//	@Override
//	public UserDto registerNewUser(UserDto user) {
//		User user = this.modelMapper.map(userDto, User.class);
//
//		// encoded the password
//		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
//
//		// roles
////		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
////
////		user.getRoles().add(role);
////
////		User newUser = this.userRepo.save(user);
////
////		return this.modelMapper.map(newUser, UserDto.class);
////	}
//
//}
