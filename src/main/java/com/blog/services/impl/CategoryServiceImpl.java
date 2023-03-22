package com.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.ICategoryRepo;
import com.blog.services.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

	// get dependacy obj
	@Autowired
	private ICategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// save takes Category entity as args so convert CategoryDto to Category entity
		Category addedCat = this.categoryRepo.save(this.modelMapper.map(categoryDto, Category.class));
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category catTobeUpdate = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Cateogory ", "categoryId ", categoryId));
		// set new values which will get through req body as categoryDto
		catTobeUpdate.setCatTitle(categoryDto.getCatTitle());
		catTobeUpdate.setCatDescription(categoryDto.getCatDescription());
		Category updatedCat = this.categoryRepo.save(catTobeUpdate);

		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category catToBeDelete = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Cateogory ", "categoryId ", categoryId));

		this.categoryRepo.delete(catToBeDelete);

	}

	@Override
	public CategoryDto getSingleCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Cateogory ", "categoryId ", categoryId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> listCat = this.categoryRepo.findAll();
		//convert each obj to CategoryDto
		List<CategoryDto> listCatDtos = listCat.stream().map(cat->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return listCatDtos;
	}

}
