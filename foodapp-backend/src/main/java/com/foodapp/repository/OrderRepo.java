package com.foodapp.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.entity.Order;
import com.foodapp.entity.Order.OrderStatus;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
	
	public Order findByOrderId(Integer id);
	
	public List<Order> findByUserId(Integer id);
	
	public List<Order> findByStatus(OrderStatus status);
	
	public List<Order> findByOrderDate(LocalDate orderDate);
	
	public List<Order> findByTotalAmountGreaterThan(Double amt);
	
}

