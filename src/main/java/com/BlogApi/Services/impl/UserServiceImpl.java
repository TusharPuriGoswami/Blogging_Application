package com.BlogApi.Services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BlogApi.Entites.Role;
import com.BlogApi.Entites.User;
import com.BlogApi.Exceptions.ResourceNotFoundException;
import com.BlogApi.Payloads.UserDto;
import com.BlogApi.Repositories.RoleRepo;
import com.BlogApi.Repositories.UserRepo;
import com.BlogApi.Services.UserService;
import com.BlogApi.config.AppConstants;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepo roleRepo;
    

    //create user api
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    //update use 
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        return this.userToDto(updatedUser);
    }

    //get single user 
    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return this.userToDto(user);
    }

    //get all user 
    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        return users.stream()
                .map(this::userToDto)
                .collect(Collectors.toList());
    }

    //delete user 
    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    public User dtoToUser(UserDto userDto) {
    	
    	User user = this.modelMapper.map(userDto,User.class);
    	
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user) {
    	
    	
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }

    //Register user
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
	User user = this.modelMapper.map(userDto, User.class);
	
	//encoded the password
	user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
	//role
	 Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
	 user.getRoles().add(role);
	 User newUser = this.userRepo.save(user);
	 
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
