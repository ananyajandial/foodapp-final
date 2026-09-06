package com.foodapp.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodapp.entity.Order;
import com.foodapp.entity.Order.OrderStatus;
import com.foodapp.exception.FoodNotAvailableException;
import com.foodapp.exception.InvalidQuantityException;
import com.foodapp.exception.OrderNotFoundException;
//import com.food.entity.Food;



@Service
public interface IOrderService {
	public String placeOrder(Order order) throws FoodNotAvailableException, InvalidQuantityException;
	public Optional<Order> viewOrder(int id); //exception is in service class
	public List<Order> allOrders();
	public String cancelOrder(int id) throws OrderNotFoundException;
	public String updateStatus(int id , OrderStatus status) throws OrderNotFoundException;
	public List<Order> findByUserId(Integer id); //Unimplemented Yet
	public List<Order> findByStatus(OrderStatus status); //exception is in service class
	public List<Order> findByPendingStatus();
	public List<Order> findByOrderDate(LocalDate orderDate);
	public List<Order> findByTotalAmountGreaterThan(Double amt);

}



