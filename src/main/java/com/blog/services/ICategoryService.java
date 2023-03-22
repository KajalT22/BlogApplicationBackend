package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface ICategoryService {

	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	//update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	//delete
	void deleteCategory(Integer categoryId);
	//get single category
	CategoryDto getSingleCategory(Integer categoryId);
	//get All categories
	List<CategoryDto> getAllCategories();
	
}
