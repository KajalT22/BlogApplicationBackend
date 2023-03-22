package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.IRoleRepo;

@SpringBootApplication
public class BlogAppBackendApplication implements CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private IRoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppBackendApplication.class, args);
	}
	
	//spring will create a obj of this model mapper hence given Bean anno 
	//we can use it anywhere by injecting this by @AutoWired
	
	//by using ModelMapper we can map our entity with dto and will return vice versa 
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	//method od CommandLineRunner 
	@Override
	public void run(String... args) throws Exception {
		//System.out.println(this.passwordEncoder.encode("abc"));
		//asssign 2 roles in db at first time 
		try {
			Role role1 = new Role();
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			//save this to db
			List<Role> roles = List.of(role1,role2);
			List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
