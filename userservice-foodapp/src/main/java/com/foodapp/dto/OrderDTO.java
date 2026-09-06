package com.foodapp.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
	
	private int orderId;
	private int userId;
	private int foodId;
	private int quantity;
	private float totalAmount;
	private String status;
	private LocalDate orderDate;

}
