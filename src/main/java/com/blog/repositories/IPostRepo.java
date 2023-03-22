package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface IPostRepo  extends JpaRepository<Post, Integer>{
	//we can create methods over here alse for eg
	//custom finder methods
	//impl class provide by spring container
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	//method for searching
	//behind the sceane it will fire a query " title like"
	List<Post> findByTitleContaining(String title);

}
