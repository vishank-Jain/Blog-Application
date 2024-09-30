package com.codewithvishank.blog.payloads;
import lombok.Data;

	@Data
	public class JwtResponse {
		private String token;
		private UserDto user;
	}
