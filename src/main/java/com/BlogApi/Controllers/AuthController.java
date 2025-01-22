
package com.BlogApi.Controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BlogApi.Entites.User;
import com.BlogApi.Exceptions.ApiException;
import com.BlogApi.Payloads.JwtAuthRequest;
import com.BlogApi.Payloads.JwtAuthResponse;
import com.BlogApi.Payloads.UserDto;
import com.BlogApi.Services.UserService;
import com.BlogApi.security.JwtTokenHelper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ModelMapper modelMapper;

    //
    //login api
    
    @PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate1(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(this.modelMapper.map((User) userDetails, UserDto.class));
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}
    
    private void authenticate1(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		try {

			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {
			System.out.println("Invalid Detials !!");
			throw new ApiException("Invalid username or password !!");
		}

	}

    
    
//    @PostMapping("login")
//    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request) {
//        try {
//            this.authenticate(request.getUsername(), request.getPassword());
//            
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
//            String token = this.jwtTokenHelper.generateToken(userDetails);
//            
//           
//            
//            JwtAuthResponse response = new JwtAuthResponse();
//            response.setToken(token);
//            
//            response.setUser(this.modelMapper.map((User) userDetails, UserDto.class));
//            
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (BadCredentialsException e) {
//        	System.out.println(e);
//            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
//           
//        } 
//        catch (Exception e) {
//        	System.out.println(e);
//            return new ResponseEntity<>("Authentication failed !", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    //JWT authentication
    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        
        this.authenticationManager.authenticate(authenticationToken);
    }
    
    //Register new user 
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
    	UserDto registeredUser =  this.userService.registerNewUser(userDto);
    	return new ResponseEntity<UserDto>(registeredUser , HttpStatus.CREATED);
    	
    }
    
    
}

