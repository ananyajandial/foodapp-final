package com.food.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_seq")
    @SequenceGenerator(
    		name = "food_seq",
    		initialValue = 200,
    		allocationSize = 1)
	
    private int foodId;

    private String foodName;
    
    private String category;

    private double price;

    private String description;

    private boolean available;

  

    public Food(String foodName, String category, double price, String description, boolean available) {
		this.foodName = foodName;
		this.category = category;
		this.price = price;
		this.description = description;
		this.available = available;
	}

}