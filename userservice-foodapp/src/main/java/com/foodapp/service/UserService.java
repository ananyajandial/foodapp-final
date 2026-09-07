package com.foodapp.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodapp.dto.OrderDTO;
import com.foodapp.dto.UserDto;
import com.foodapp.entity.User;

public interface UserService {

	
	public User registerUser(User users);

	public User getUserById(Integer id);

	public List<User> getAllUsers();

	public User updateUser(Integer id, UserDto userdto);

	public User deleteUser(Integer id);

	public User getUserByEmail(String email);

	public User getUserByPhoneNumber(String phonenumber);

	public List<User> getUserByName(String name);

	public List<OrderDTO> getAllOrders();



}
