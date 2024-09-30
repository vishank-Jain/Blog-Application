package com.codewithvishank.blog.payloads;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
	@NotEmpty
	private int id;
	@Email(message = "Email not valid")
	private String email;
	
	@NotEmpty
	@Size(min=4,message="Not valid Name")
	private String name;
	@NotEmpty
	private String password;
	@NotEmpty
	private String about;
	
	
}
