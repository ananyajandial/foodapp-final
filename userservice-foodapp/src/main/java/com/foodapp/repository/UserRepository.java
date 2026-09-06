package com.foodapp.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.entity.User;


	@Repository
	public interface UserRepository extends JpaRepository<User,Integer>{

		Optional<User> findByEmail(String email);

		Optional<User> findByPhonenumber(String phonenumber);

		List<User> findByName(String name);
		
	}
