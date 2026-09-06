package com.foodapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.foodapp.entity.Order;
import com.foodapp.repository.OrderRepo;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
	
	    @InjectMocks
	    private OrderServiceImpl service;

	    @Mock
	    private OrderRepo repo;
	    
	    //positive
	    @Test
	    void testViewOrder() {

	        Order order = new Order();

	        when(repo.findById(1))
	                .thenReturn(Optional.of(order));

	        Optional<Order> result =
	                service.viewOrder(1);

	        assertTrue(result.isPresent());
	    }
	    
	    //negative
	    @Test
	    void testViewOrder_NotFound() {

	        when(repo.findById(1))
	                .thenReturn(Optional.empty());

	        Optional<Order> result =
	                service.viewOrder(1);

	        assertFalse(result.isPresent());
	    }
	    
	    @Test
	    void testAllOrders() {

	        List<Order> list = new ArrayList<>();
	        list.add(new Order());

	        when(repo.findAll())
	                .thenReturn(list);

	        List<Order> result =
	                service.allOrders();

	        assertEquals(1, result.size());
	    }

	    //negative 
	    @Test
	    void testAllOrders_EmptyList() {

	        when(repo.findAll())
	                .thenReturn(new ArrayList<>());

	        List<Order> result = service.allOrders();

	        assertTrue(result.isEmpty());
	    }
	    
	    
	   
	}

