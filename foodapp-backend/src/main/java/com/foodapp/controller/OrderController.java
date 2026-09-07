package com.foodapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodapp.entity.Order;
import com.foodapp.entity.Order.OrderStatus;
import com.foodapp.exception.FoodNotAvailableException;
import com.foodapp.exception.InvalidQuantityException;
import com.foodapp.exception.OrderNotFoundException;
import com.foodapp.service.IOrderService;

import io.swagger.v3.oas.annotations.Operation;

//import com.food.entity.Food;


@RestController
@RequestMapping("/orders")

public class OrderController {
	@Autowired
	IOrderService service;

	
	@PostMapping("/placeorder")
	public ResponseEntity<String> placeOrder(@RequestBody Order newOrder) throws FoodNotAvailableException, InvalidQuantityException{
		String notification =  service.placeOrder(newOrder);
		return new ResponseEntity<String>(notification , HttpStatus.CREATED);
		
	}
	
	@GetMapping("/view/id/{id}")
	public ResponseEntity<List<Order>> viewOrder(@PathVariable int id) throws OrderNotFoundException{
		List<Order> getOrder =  service.findByUserId(id);
		return new ResponseEntity<List<Order>>(getOrder , HttpStatus.OK);
		
	}
	
	@GetMapping("/allOrders")
	public ResponseEntity<List<Order>> allOrders() throws OrderNotFoundException{
		List<Order> getOrder =  service.allOrders();
		if (getOrder.isEmpty()) {
			throw new OrderNotFoundException("No Order created yet");
		}
		return new ResponseEntity<List<Order>>(getOrder , HttpStatus.OK);
	}
	
	@GetMapping("/cancel/{id}")
	public ResponseEntity<String> cancelOrder(@PathVariable int id) throws OrderNotFoundException{
		String notification =  service.cancelOrder(id);
		return new ResponseEntity<String>(notification , HttpStatus.OK);
	}
	
	@PatchMapping("/update/status/{id}")
	private ResponseEntity<String> updateStatus(@PathVariable int id , @RequestBody Order.OrderStatus status) throws OrderNotFoundException{
		String notification =  service.updateStatus(id, status);
		return new ResponseEntity<String>(notification , HttpStatus.OK);
	}
	
	@GetMapping("/view/status/{status}")
	public ResponseEntity<List<Order>> viewOrder(@PathVariable OrderStatus status) throws OrderNotFoundException{
		List<Order> getOrder =  service.findByStatus(status);
		if (getOrder.isEmpty()){
			throw new OrderNotFoundException("No Order with status : " +status);
		}
		return new ResponseEntity<List<Order>>(getOrder , HttpStatus.OK);
	}
	
	@GetMapping("/view/date/{date}")
	public ResponseEntity<List<Order>> viewOrder(@PathVariable LocalDate date) throws OrderNotFoundException{
		List<Order> getOrder =  service.findByOrderDate(date);
		if (getOrder.isEmpty()) {
			throw new OrderNotFoundException("No Order on date : " +date);
		}
		return new ResponseEntity<List<Order>>(getOrder , HttpStatus.OK);
	}
	
	@GetMapping("/view/cancelled")
	public ResponseEntity<List<Order>> viewOrder() throws OrderNotFoundException{
		List<Order> getOrder =  service.findByStatus(OrderStatus.cancelled);
		if (getOrder.isEmpty()){
			throw new OrderNotFoundException("No Order with status : Cancelled");
		}
		return new ResponseEntity<List<Order>>(getOrder , HttpStatus.OK);
	}
	
	@GetMapping("/view/pending")
	public ResponseEntity<List<Order>> findByPendingStatus() throws OrderNotFoundException{
		List<Order> getOrder =  service.findByPendingStatus();
		if (getOrder.isEmpty()){
			throw new OrderNotFoundException("No Pending Orders");
		}
		return new ResponseEntity<List<Order>>(getOrder , HttpStatus.OK);
	}
	
	@GetMapping("/viewOrdersAbove/{amt}")
	public ResponseEntity<List<Order>> findByTotalAmountGreaterThan(@PathVariable Double amt) throws OrderNotFoundException{
		List<Order> getOrder =  service.findByTotalAmountGreaterThan(amt);
		if (getOrder.isEmpty()) {
			throw new OrderNotFoundException("No Order above rs. : " +amt);
		}
		return new ResponseEntity<List<Order>>(getOrder , HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<Order>> findByUserId( @PathVariable Integer userId)
	        throws OrderNotFoundException {

	    List<Order> orders = service.findByUserId(userId);

	    if (orders.isEmpty()) {
	        throw new OrderNotFoundException(
	                "No previous orders for : " + userId);
	    }

	    return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
} 
	
