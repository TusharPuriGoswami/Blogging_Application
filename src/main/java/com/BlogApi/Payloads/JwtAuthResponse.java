package com.BlogApi.Payloads;


import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token;
	private UserDto user;

}
