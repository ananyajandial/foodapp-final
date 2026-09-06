package com.foodapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FoodOrders")
public class Order {
	
	public enum OrderStatus{
		placed , preparing , delivered , cancelled
	}
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
	@SequenceGenerator(
	name = "order_seq",
	initialValue = 300,
	allocationSize = 1
	)
	
    @Schema(
            description = "Auto-generated Order ID", 
            accessMode = Schema.AccessMode.READ_ONLY
        )
	private int orderId;
	
    @Schema(
            example = "100"
        )
	private int userId;
    
    @Schema(
            example = "200"
        )
	private int foodId;
    
    @Schema(
            description = "It must lie between 1-10"
        )
	private int quantity;
    
    @Schema(
            description = "This will be calculated automatically",
            accessMode = Schema.AccessMode.READ_ONLY
        )
	private double totalAmount;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
    @Schema(
            description = "It must be of format YYYY-MM-DD"
        )
	private LocalDate orderDate;
	public Order(int userId, int foodId, int quantity) {
		this.userId = userId;
		this.foodId = foodId;
		this.quantity = quantity;
	}
	

}
