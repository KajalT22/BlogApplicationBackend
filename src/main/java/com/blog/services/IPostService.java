package com.blog.services;

import java.util.List;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface IPostService {
	
	//create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get AlL posts
	PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir);
	
	//get Single post
	PostDto getSinglePost(Integer postId);
	
	//get All posts by Category(FK)
	List<PostDto> getAllPostsByCategory(Integer categoryId);
	
	//get All posts by User(FK)
	List<PostDto> getAllPostsByUser(Integer userId);
	
	//search post by keyword
	List<PostDto> searchPosts(String keyword);

}
