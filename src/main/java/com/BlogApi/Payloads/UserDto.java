package com.BlogApi.Payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.BlogApi.Entites.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
	@Size(min = 4 , message = "Username must be min of 4 characters !!")
	private String name;
	
	@Email(message = "Email is not valid !!")
	@NotEmpty(message = "email is Required !!")
	private String email;
	
	@NotEmpty
	@Size(min = 3,max = 10 , message = "Password must be min of 3 char and max of 10 char !!")
	private String password;
	
	@NotEmpty
	@Size(min = 4 , message = "About must be min of 4 characters!!")
	private String about;
	
	
	private Set<RoleDto> roles = new HashSet();
	
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	
}
