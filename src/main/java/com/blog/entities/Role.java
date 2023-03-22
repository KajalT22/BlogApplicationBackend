package com.blog.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role {
	//we will not genrate auto role id we have 3-4 roles that will genrate rols auto while resgistering
	@Id
	private Integer id;
	private String name;
	


}
