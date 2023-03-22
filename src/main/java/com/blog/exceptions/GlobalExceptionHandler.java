package com.blog.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String msg = ex.getMessage();
		//pass msg along with success status to the ApiResponse
		ApiResponse apiResponse = new ApiResponse(msg, false); //send the same response with status code 
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	//MethodArgumentNotValidException will get we if pass any invalid details as per userDto anno
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> mapResp = new HashMap<String, String>();
		//get messeage (which we passed in UserDto anno)and feild for which excep occurs
		ex.getBindingResult().getAllErrors().forEach( (error)->{
			String fieldName = ((FieldError) error).getField();
			String msg = error.getDefaultMessage();
			//add feildname and msg to map 
			mapResp.put(fieldName, msg);
		});
		return new ResponseEntity<Map<String,String>>(mapResp,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(AuthApiException.class)
	public ResponseEntity<ApiResponse> authApiExceptionHandler(AuthApiException ex){
		String msg = ex.getMessage();
		//pass msg along with success status to the ApiResponse
		ApiResponse apiResponse = new ApiResponse(msg, false); //send the same response with status code 
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
	}

}
