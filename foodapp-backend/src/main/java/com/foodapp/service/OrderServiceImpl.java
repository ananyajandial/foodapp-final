package com.foodapp.service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodapp.client.FoodFeignClient;
import com.foodapp.client.UserFeignClient;
import com.foodapp.dto.FoodDTO;
import com.foodapp.dto.UserDTO;
import com.foodapp.entity.Order;
import com.foodapp.entity.Order.OrderStatus;
import com.foodapp.exception.FoodNotAvailableException;
import com.foodapp.exception.InvalidQuantityException;
import com.foodapp.exception.OrderNotFoundException;
import com.foodapp.repository.OrderRepo;

@Service
public class OrderServiceImpl implements IOrderService {

    // 1. Mark both dependencies as private final
    private final OrderRepo orderrepo;
    private final FoodFeignClient foodFeignClient;
    private final UserFeignClient userFeignclient;

    // 2. Inject both via a single constructor
    @Autowired
    public OrderServiceImpl(OrderRepo orderrepo, FoodFeignClient foodFeignClient , UserFeignClient userFeignclient) {
        this.orderrepo = orderrepo;
        this.foodFeignClient = foodFeignClient;
        this.userFeignclient = userFeignclient;

    }

    @Override
    public String placeOrder(Order order) throws FoodNotAvailableException, InvalidQuantityException {
        if (order.getQuantity() > 10 ) {
            throw new InvalidQuantityException("You cannot order more than 10 items at once.");
        }
        if (order.getQuantity() <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero.");
        }

        int foodId = order.getFoodId();
        FoodDTO foodDto = foodFeignClient.getFoodById(foodId);
        
        if (foodDto == null) {
        	throw new FoodNotAvailableException("Sorry .. Food Item with id : "+foodId +" does not exist");
        }
        
        if (foodDto.isAvailable() == false) {
        	throw new FoodNotAvailableException("Sorry .. Food Item Is Out Of Stock at this moment");
        }
        
        int userId = order.getUserId();
        UserDTO userDto = userFeignclient.getUserById(userId);
        
        if (userDto == null) {
        	throw new FoodNotAvailableException("User "+userId +" does not exist!! Create new user");
        }
        
        double price = foodDto.getPrice();
        order.setTotalAmount(order.getQuantity() * price);
        
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.placed);
        }
        order.setOrderDate(LocalDate.now());
        
        orderrepo.save(order);
        return "Order Placed !! Order ID: " + order.getOrderId();
    }


	@Override
	public Optional<Order> viewOrder(int id) {
		// TODO Auto-generated method stub
			return orderrepo.findById(id);
	}

	@Override
	public List<Order> allOrders() {
		// TODO Auto-generated method stub
		return orderrepo.findAll();

	}
	

	@Override
	public String cancelOrder(int id) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		Optional<Order> oldOrder = orderrepo.findById(id);
		if (oldOrder.isPresent()) {
			Order cancel = orderrepo.findByOrderId(id);
			cancel.setStatus(OrderStatus.cancelled);
			orderrepo.save(cancel);
			return "Order Status Changed to Cancelled";			
		}
		else {
			throw new OrderNotFoundException("No order for order id : "+id);
		}

	}

	@Override
	public String updateStatus(int id , OrderStatus status) throws OrderNotFoundException {
		// TODO Auto-generated method stub
		Optional<Order> oldOrder = orderrepo.findById(id);
		
		if (oldOrder.isPresent()) {
			Order changeStatus = orderrepo.findByOrderId(id);
			changeStatus.setStatus(status);
			orderrepo.save(changeStatus);
			return "Order Status Changed to " +status;		
		}
		else {
			throw new OrderNotFoundException("No order for order id : "+id);
		}


	}


	@Override
	public List<Order> findByUserId(Integer id) {
		return orderrepo.findByUserId(id);

	}

	@Override
	public List<Order> findByStatus(OrderStatus status) {
		return orderrepo.findByStatus(status);

	}
	
	public List<Order> findByPendingStatus() {
		List<Order> preparingOrders = orderrepo.findByStatus(OrderStatus.preparing);
		List<Order> placedOrders = orderrepo.findByStatus(OrderStatus.placed);
		
	    List<Order> combinedOrders = new ArrayList<>(preparingOrders);
	    
	    combinedOrders.addAll(placedOrders);

		return combinedOrders;

	}

	@Override
	public List<Order> findByOrderDate(LocalDate orderDate) {		
		return orderrepo.findByOrderDate(orderDate);	
		}


	@Override
	public List<Order> findByTotalAmountGreaterThan(Double amt) {
		return orderrepo.findByTotalAmountGreaterThan(amt);
	}

}