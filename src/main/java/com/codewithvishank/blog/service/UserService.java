package com.codewithvishank.blog.service;

import java.util.List;

import com.codewithvishank.blog.payloads.UserDto;

public interface UserService {
	UserDto createUser(UserDto User);
	UserDto	updateUser(UserDto User,Integer id);
	UserDto getUser(Integer id);
	List<UserDto> getAllUser();
	void deleteUser(Integer id);
//	UserDto registerNewUser(UserDto user);
	UserDto registerNewUser(UserDto userDto);
}
