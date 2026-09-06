package com.foodapp.exception;


import org.springframework.web.bind.annotation.RestController;

	public class UserNotFoundException extends RuntimeException{
	   public UserNotFoundException(String message) {
		   super (message);
	   }
	}

