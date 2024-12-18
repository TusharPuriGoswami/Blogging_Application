
package com.BlogApi.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.BlogApi.Payloads.JwtAuthRequest;
import com.BlogApi.Payloads.JwtAuthResponse;
import com.BlogApi.Payloads.UserDto;
import com.BlogApi.Services.UserService;
import com.BlogApi.security.JwtTokenHelper;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;

    //Post api
    @PostMapping("login")
    public ResponseEntity<?> createToken(@RequestBody JwtAuthRequest request) {
        try {
            this.authenticate(request.getUsername(), request.getPassword());
            
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
            String token = this.jwtTokenHelper.generateToken(userDetails);
            
            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(token);
            
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>("Authentication failed !", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //JWT authentication
    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        
        this.authenticationManager.authenticate(authenticationToken);
    }
    
    //Register new user 
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
    	UserDto registeredUser =  this.userService.registerNewUser(userDto);
    	return new ResponseEntity<UserDto>(registeredUser , HttpStatus.CREATED);
    	
    }
    
    
}

