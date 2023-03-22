package com.blog.services;

import com.blog.payloads.CommentDto;

public interface ICommentService {
	
	//create/add comment
	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	//delete comment
	void deleteComment(Integer commentId);

}
