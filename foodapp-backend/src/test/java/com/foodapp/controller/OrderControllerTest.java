package com.foodapp.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.foodapp.entity.Order;
import com.foodapp.exception.FoodNotAvailableException;
import com.foodapp.exception.OrderNotFoundException;
import com.foodapp.service.IOrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController controller;

    @Mock
    private IOrderService service;

    @Test
    void testViewOrder() throws Exception {

        Order order = new Order();

        when(service.viewOrder(1))
                .thenReturn(Optional.of(order));

        ResponseEntity<Order> response =
                controller.viewOrder(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
//negative
    
    @Test
    void testViewOrder_NotFound() {

        when(service.viewOrder(1))
                .thenReturn(Optional.empty());

        assertThrows(
                OrderNotFoundException.class,
                () -> controller.viewOrder(1)
        );
    }
    
    
    @Test
    void testAllOrders() throws Exception {

        List<Order> orders = new ArrayList<>();
        orders.add(new Order());

        when(service.allOrders())
                .thenReturn(orders);

        ResponseEntity<List<Order>> response =
                controller.allOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //negative 
    @Test
    void testAllOrders_NotFound() throws Exception {
    	when(service.allOrders()).thenReturn(new ArrayList<>());
    	assertThrows(OrderNotFoundException.class,()->controller.allOrders() );
    	}
    	
    
    @Test
    void testCancelOrder() throws Exception {

        when(service.cancelOrder(1))
                .thenReturn("Order Cancelled");

        ResponseEntity<String> response =
                controller.cancelOrder(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Order Cancelled", response.getBody());
    }
    
    //negative
    @Test
    void testCancelOrder_Notfound() throws Exception {
    	when(service.cancelOrder(1)).thenThrow(new OrderNotFoundException("order not found"));
    	assertThrows(OrderNotFoundException.class,()->controller.cancelOrder(1));
    	
    }

    @Test
    void testPlaceOrder() throws Exception {

        Order order = new Order();

        when(service.placeOrder(order))
                .thenReturn("Order Placed Successfully");

        ResponseEntity<String> response =
                controller.placeOrder(order);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Order Placed Successfully", response.getBody());
    }
    

    @Test
    void testPlaceOrder_Notfound() throws Exception {
    	 Order order = new Order();
    	when(service.placeOrder(order)).thenThrow(new FoodNotAvailableException("food not available"));
    	
    	assertThrows(FoodNotAvailableException.class,()-> controller.placeOrder(order));
    }
    
}