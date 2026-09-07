package com.foodapp.controller;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.dto.OrderDTO;
import com.foodapp.dto.UserDto;
import com.foodapp.entity.User;
import com.foodapp.exception.InvalidUserDataException;
import com.foodapp.feign.OrderClient;
import com.foodapp.service.UserService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Validated
@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private OrderClient orderClient;

//	public UserController() {
//		System.out.println("-----Ready to exceute----");
//	}
	
    @Transactional
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@Valid @RequestBody User users) {

		logger.info("Create user Request API CAlled");
		User savedRegisterUser = userService.registerUser(users);
		return new ResponseEntity<>(savedRegisterUser, HttpStatus.CREATED);
	}

	@GetMapping("/userId/{id}")                                    //admin
	public ResponseEntity<User> getUserById(@PathVariable Integer id) {
		if(id == null) {
			throw new InvalidUserDataException("User ID cannot be null");
			}
		logger.info("Status API Called for Request Id{}",id);
		return ResponseEntity.ok(userService.getUserById(id));

	}

	@GetMapping("/all")                                                 //admin
	public ResponseEntity<List<User>> getAllUsers() {
		logger.info("Status API Called for All Users");
		return ResponseEntity.ok(userService.getAllUsers());

	}

	@PutMapping("update/{id}")                                        //admin
	public ResponseEntity<User> updateUser(@PathVariable Integer id, @Valid @RequestBody UserDto userdto) {
		logger.info("Status API Called for All updateUsers");
		return ResponseEntity.ok(userService.updateUser(id, userdto));
	}
	
	@DeleteMapping("/delete/{id}")                                         //admin
	public ResponseEntity<String> deleteUser(@PathVariable Integer id){
		logger.info("Delete user with id {}",id);
	userService.deleteUser(id);
	return ResponseEntity.ok("User Deleted Successfully");
	
	}
	
	@GetMapping("/email/{email}")                                            //admin
	public ResponseEntity<User> getUserByEmail(@PathVariable @Email(message="Invalid Email Format" ) String email){
		logger.info("Status API Called for UserbyEmail");
		return ResponseEntity.ok(userService.getUserByEmail(email));
	}
	
	@GetMapping("phone/{phonenumber}")                                   //admin
	public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phonenumber){
		logger.info("Status API Called for UserbyPhoneNumber");
		return ResponseEntity.ok(userService.getUserByPhoneNumber(phonenumber));
	}
	
	@GetMapping("/name/{name}")                                 //admin
	public ResponseEntity <List<User>> getUserByName(@PathVariable String name){
		logger.info("Status API Called for UserbyName");
		return ResponseEntity.ok(userService.getUserByName(name));
	}
	
	
	
//	//------> for orders------>
//	
//	@GetMapping("/orders")
//	public ResponseEntity<List<OrderDTO>> allOrders(){
//		logger.info("Status API Called for getAllOrders Of Users");
//		return ResponseEntity.ok(orderClient.allOrders());
//		
//	}
}


