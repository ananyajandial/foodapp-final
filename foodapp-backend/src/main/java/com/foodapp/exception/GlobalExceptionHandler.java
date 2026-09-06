package com.foodapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import feign.FeignException;

@ControllerAdvice
public class GlobalExceptionHandler {
				
		@ExceptionHandler(OrderNotFoundException.class)
		public ResponseEntity<String> handleOrderNotFoundException
		(OrderNotFoundException ex){
			String response = new String(ex.getMessage());

			return new ResponseEntity<String>(response , HttpStatus.EXPECTATION_FAILED);
		}
		
		@ExceptionHandler(FoodNotAvailableException.class)
		public ResponseEntity<String> handleFoodNotAvailableException
		(FoodNotAvailableException ex){
			String response = new String(ex.getMessage());

			return new ResponseEntity<String>(response , HttpStatus.EXPECTATION_FAILED);
		}
		
		@ExceptionHandler(UserDoesNotExistException.class)
		public ResponseEntity<String> handleUserDoesNotExistException
		(UserDoesNotExistException ex){
			String response = new String(ex.getMessage());

			return new ResponseEntity<String>(response , HttpStatus.EXPECTATION_FAILED);
		}
		
		@ExceptionHandler(FeignException.class)
		public ResponseEntity<String> handleFeignException(FeignException ex) {

		    return new ResponseEntity<String>("Please enter correct food/user ID",HttpStatus.NOT_FOUND);
		}
		
		@ExceptionHandler(MethodArgumentTypeMismatchException.class)
		public ResponseEntity<String> handleTypeMismatch(
		        MethodArgumentTypeMismatchException ex) {

		    return new ResponseEntity<>(
		            "Invalid date format. Please use yyyy-MM-dd",
		            HttpStatus.BAD_REQUEST);
		}
		
		@ExceptionHandler(InvalidQuantityException.class)
		public ResponseEntity<String> handleInvalidQuantityException(InvalidQuantityException ex) {
			
			String response = new String(ex.getMessage());
			
			return new ResponseEntity<String>(response , HttpStatus.EXPECTATION_FAILED);
		    }


}
