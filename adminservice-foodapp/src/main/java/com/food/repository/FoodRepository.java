package com.food.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.entity.Food;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    // Find Food By Name
    Food findByFoodName(String foodName);
    
    Food findByFoodId(int id);


    // Find Food By Category
    List<Food> findByCategoryContainingIgnoreCase(String category);

    // Find Food Below Particular Price
    List<Food> findByPriceLessThan(Double price);

    // Find Available Food
    List<Food> findByAvailableTrue();

    // Find Food Between Two Prices
    List<Food> findByPriceBetween(Double minPrice, Double maxPrice);
}

//Added findByFoodId