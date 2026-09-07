package com.food.dto;

import com.food.entity.Food;
import lombok.Data;

@Data
public class FoodDTO {

    private String foodName;

    private Food.category category;

    private Double price;

    private String description;

    private Boolean available;
}