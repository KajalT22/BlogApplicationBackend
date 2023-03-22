package com.blog.exceptions;


public class ResourceNotFoundException extends RuntimeException{
	String resourceName; //user
	String feildName; //id
	long feildValue; //value inside userid
	
	public ResourceNotFoundException(String resourceName, String feildName, long feildValue) {
		super(String.format("%s not found with %s : %s",resourceName,feildName,feildValue));
		this.resourceName = resourceName;
		this.feildName = feildName;
		this.feildValue = feildValue;
	}
	

}
