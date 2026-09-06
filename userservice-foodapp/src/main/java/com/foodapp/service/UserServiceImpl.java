package com.foodapp.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.foodapp.dto.OrderDTO;
import com.foodapp.entity.User;
import com.foodapp.exception.InvalidUserDataException;
import com.foodapp.exception.UserNotFoundException;
import com.foodapp.feign.OrderClient;
import com.foodapp.repository.UserRepository;

import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;

	@Service
	public class UserServiceImpl implements UserService {

		@Autowired
		private OrderClient orderClient;
		
		
		@Autowired
		UserRepository userRepository;

		@Override
		public User registerUser(User users) {
			
			if(userRepository.findByEmail(users.getEmail()).isPresent()) {
				throw new InvalidUserDataException("User Already Exists With Email:" + users.getEmail());
			}
			return userRepository.save(users);
		}

		@Override
		public User getUserById(Integer id) {
			return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
		}

		@Override
		public List<User> getAllUsers() {
			return userRepository.findAll();
		}

		@Override
		public User updateUser(Integer id, User user) {
			User existingUser = userRepository.findById(id)
					.orElseThrow(() -> new UserNotFoundException("User Not Found With Id :" + id));
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			existingUser.setPhonenumber(user.getPhonenumber());
			existingUser.setAddress(user.getAddress());

			return userRepository.save(existingUser);
		}

		@Override
		public User deleteUser(Integer id) {
			User extistingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
			userRepository.delete(extistingUser);
			return extistingUser;
		}

		@Override
		public User getUserByEmail(String email) {
			return userRepository.findByEmail(email)
					.orElseThrow(() -> new UserNotFoundException("User not Found with email:" + email));

		}

		@Override
		public User getUserByPhoneNumber(String phonenumber) {
			return userRepository.findByPhonenumber(phonenumber)
					.orElseThrow(() -> new UserNotFoundException("User not Found with phonenumber:"+phonenumber));
		}
		
		@Override
		public List<User> getUserByName(String name) {
			List<User> users= userRepository.findByName(name);
			if(users.isEmpty()) {
				throw new UserNotFoundException("User Not Found With Name :"+name);
			}
			return users;
					
			
		}
		
		//------- for orders ---------
		@Override
		public List<OrderDTO> getAllOrders(){
			return orderClient.allOrders();
	}
	

	}

