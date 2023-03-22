package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.ICategoryRepo;
import com.blog.repositories.IPostRepo;
import com.blog.repositories.IUserRepo;
import com.blog.services.IPostService;

@Service
public class PostServiceImpl implements IPostService {
	
	//get depandancy
	@Autowired
	private IPostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	//for getting user and category
	@Autowired
	private IUserRepo userRepo;
	@Autowired
	private ICategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		//get user n category for setting user n category in post
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		
		Post mappedPost = this.modelMapper.map(postDto, Post.class);
		//postDto has only 2properties title n content so set others here
		mappedPost.setImageName("default.png");
		mappedPost.setAddedDate(new Date());
		mappedPost.setUser(user);
		mappedPost.setCategory(category);
		
		Post createdPost = this.postRepo.save(mappedPost);
		
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post postToBeUpdate = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post id", postId));
			
		Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();
		
		postToBeUpdate.setTitle(postDto.getTitle());
		postToBeUpdate.setContent(postDto.getContent());
		postToBeUpdate.setImageName(postDto.getImageName());
		postToBeUpdate.setCategory(category);
		//postToBeUpdate.setAddedDate(postDto.getAddedDate());
		Post updatedPost = this.postRepo.save(postToBeUpdate);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post id", postId));
		this.postRepo.delete(post);
	}

	//get All posts returns PoastReponse as it contains post details along with page details 
	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir) {
		//we use FindAll(Pageable obj) 
//		int pageSize=5;
//		int pageNumber=1;
		//we will take pagesize n pagenumber through request param
		
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> postList = pagePost.getContent();
		
		List<PostDto> listPostDtos = postList.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		//create obj of postResponse to get all the details
		PostResponse postResponse = new PostResponse();
		postResponse.setPostContent(listPostDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getSinglePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "Post id", postId));
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "category id", categoryId));
		//call post repo method that we created //List<Post> findByCategory(Category category);
		List<Post> posts = this.postRepo.findByCategory(cat);
		//convert to List<PostDto>
		List<PostDto> listPostDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return listPostDtos;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id", userId));
		List<Post> postList = this.postRepo.findByUser(user);
		List<PostDto> listPostDtos = postList.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return listPostDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> postList = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> listPostDtos = postList.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return listPostDtos;
	}

}
