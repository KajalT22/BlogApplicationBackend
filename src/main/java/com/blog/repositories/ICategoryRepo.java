package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;

public interface ICategoryRepo extends JpaRepository<Category, Integer>{
	
	

}
