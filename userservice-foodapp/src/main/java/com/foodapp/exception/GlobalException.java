package com.foodapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(InvalidUserDataException.class)
	public ResponseEntity<String> handleInvalidUserData(InvalidUserDataException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
		String message = ex.getBindingResult().getFieldError().getDefaultMessage();

		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
		String message = ex.getConstraintViolations().iterator().next().getMessage();

		return new ResponseEntity<>("Validation Failed : " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(FeignException.class)
	public ResponseEntity<String> handleFeignException(FeignException ex) {
		
		String response = new String(ex.getMessage());
		
		return new ResponseEntity<String>(response , HttpStatus.EXPECTATION_FAILED);
	    }
}
