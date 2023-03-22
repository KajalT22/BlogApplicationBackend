package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	@NotEmpty(message ="name cannot be empty!!!")
	private String name;
	@Email(message = "email address is not valid!!!")
	@NotEmpty(message = "Email is required !!")
	private String email;
	@NotEmpty
	@Size(min = 3, max = 8, message = "password must be within range 3 to 8!!!!")
	private String password;
	@NotEmpty
	private String about;
	private Set<RoleDto> roles = new HashSet<>();
	
	//avoid getting password displayed on colsole
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}
	
	//dont ignore while setting the password
	@JsonProperty
	public void setPassword(String password) {
		this.password=password;
	}

}
