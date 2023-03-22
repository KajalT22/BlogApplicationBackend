package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.ICommentRepo;
import com.blog.repositories.IPostRepo;
import com.blog.services.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {
	
	@Autowired
	private ICommentRepo commentRepo;
	@Autowired
	private IPostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post Id", postId));
		//convert commentDto to comment
		Comment comment = this.modelMapper.map(commentDto,Comment.class);
		//set post for particuler comment
		comment.setPost(post);
		//save the comment
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow( ()->new ResourceNotFoundException("Comment", "comment Id", commentId));
		this.commentRepo.delete(comment);
	
	}

}
