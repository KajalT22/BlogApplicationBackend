package com.blog.services;

import java.util.List;

import com.blog.payloads.UserDto;

public interface IUserService {
	
	//here we can directly deal with enties like User but we are not doing that 
	//for that we are using Dto enties which is inside payloads
	//as we dont want to expose our entities directly to api
	UserDto createUser(UserDto user);
	
	//for update user by id
	UserDto updateUser(UserDto user, Integer userId);
	
	//for get user by id
	UserDto getUser(Integer userId);
	
	//for get ALL users
	List<UserDto> getAllUsers();
	
	//delete user by id
	void deleteUser(Integer userId);
	
	//register user
	UserDto registerNewUser(UserDto userDto);
	

}
