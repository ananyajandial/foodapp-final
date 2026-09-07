package com.food.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	
	public enum category{
		FASTFOOD, NORTHINDIAN, SOUTHINDIAN, CHINESE, DESSERT
	}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "food_seq")
    @SequenceGenerator(
    		name = "food_seq",
    		initialValue = 200,
    		allocationSize = 1)
	
    private int foodId;

    private String foodName;
    
    @Enumerated(EnumType.STRING)
    private category category;

    private double price;

    private String description;

    private boolean available;

	public Food(String foodName, category category, double price, String description,
			boolean available) {
		super();
		this.foodName = foodName;
		this.category = category;
		this.price = price;
		this.description = description;
		this.available = available;
	}
}