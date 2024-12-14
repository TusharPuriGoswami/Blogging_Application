package com.BlogApi.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

//       (Data transfer object)
public class UserDto {

	private int id;
	
	@NotEmpty
	@Size(min = 4 , message = "Username must be min of 4 characters")
	private String name;
	
	@Email(message = "Email is not valid")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 10 , message = "Password must be min of 3 char and max of 10 char!!")
	private String password;
	
	@NotEmpty
	private String about;
	
}
