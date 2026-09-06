package com.food.dto;
  
import lombok.Data;
 
 
@Data
public class FoodDTO {
	private String foodName;
	private String category;
	private Double price;
	private String description;
	private Boolean available;
 
}

