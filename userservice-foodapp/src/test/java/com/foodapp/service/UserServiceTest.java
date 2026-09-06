package com.foodapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foodapp.entity.User;
import com.foodapp.exception.UserNotFoundException;
import com.foodapp.feign.OrderClient;
import com.foodapp.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderClient orderClient;

    @InjectMocks
    private UserServiceImpl service;

    
    //getuserbyId
    //positive
    @Test
    void testGetUserById() {

        User user = new User();
        user.setUserId(1);

        when(userRepository.findById(1))
                .thenReturn(Optional.of(user));

        User result = service.getUserById(1);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }
//negative
    
    @Test
    void testGetUserById_NotFound() {

        when(userRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.getUserById(1)
        );
    }
    
    
    //for the registerUser
    @Test
    void testRegisterUser() {

        User user = new User();
        user.setName("Khushbu");
        user.setEmail("khushbu@gmail.com");

        when(userRepository.findByEmail("khushbu@gmail.com"))
                .thenReturn(Optional.empty());

        when(userRepository.save(user))
                .thenReturn(user);

        User savedUser = service.registerUser(user);

        assertNotNull(savedUser);
        assertEquals("Khushbu", savedUser.getName());
    }
    
// for the UserByEmail
//positive 
    @Test
    void testGetUserByEmail() {

        User user = new User();
        user.setEmail("khushbu@gmail.com");

        when(userRepository.findByEmail("khushbu@gmail.com"))
                .thenReturn(Optional.of(user));

        User result =
                service.getUserByEmail("khushbu@gmail.com");

        assertNotNull(result);
        assertEquals(
                "khushbu@gmail.com",
                result.getEmail());
    }
    
//negative
    @Test
    void testGetUserByEmail_NotFound() {

        when(userRepository.findByEmail("abc@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.getUserByEmail("abc@gmail.com")
        );
    }

    // for the getUserByPhoneNumber
    // positive
    @Test
    void testGetUserByPhoneNumber() {

        User user = new User();
        user.setPhonenumber("9876543210");

        when(userRepository.findByPhonenumber("9876543210"))
                .thenReturn(Optional.of(user));

        User result =
                service.getUserByPhoneNumber("9876543210");

        assertNotNull(result);
         
        assertEquals(
                "9876543210",
                result.getPhonenumber());
    }

    //negative
    
    @Test
    void testGetUserByPhonenumber_NotFound() {

        when(userRepository.findByPhonenumber("9876543210"))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> service.getUserByPhoneNumber("9876543210")
        );
    }
    
    //for GetUsersbyName
    //positive
    @Test
    void testGetUserByName() {

        User user = new User();
        user.setName("Khushbu");

        when(userRepository.findByName("Khushbu"))
                .thenReturn(List.of(user));

        assertEquals(
                1,
                service.getUserByName("Khushbu").size());
    }
    
    //negative
    @Test
    void testGetUserByName_NotFound() {

        when(userRepository.findByName("ABC"))
                .thenReturn(Collections.emptyList());

        assertThrows(
                UserNotFoundException.class,
                () -> service.getUserByName("ABC")
        );
    }
    
}
