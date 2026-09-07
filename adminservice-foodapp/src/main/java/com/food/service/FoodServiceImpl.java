package com.food.service;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.dto.FoodDTO;
import com.food.entity.Food;
import com.food.exception.FoodAlreadyExistsException;
import com.food.exception.FoodNotFoundException;
import com.food.repository.FoodRepository;

@Service
public class FoodServiceImpl implements FoodService {

    private static final Logger logger =
            LoggerFactory.getLogger(FoodServiceImpl.class);

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food addFood(Food food) {

        logger.info("Adding Food : {}", food.getFoodName());

        Food existingFood =
                foodRepository.findByFoodName(food.getFoodName());

        if (existingFood != null) {
            throw new FoodAlreadyExistsException(
                    "Food already exists with name : "
                            + food.getFoodName());
        }

        return foodRepository.save(food);
    }

    @Override
    public Food getFoodById(Integer foodId) {

        logger.info("Fetching Food with Id : {}", foodId);

        return foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new FoodNotFoundException(
                                "Food not found with id : "
                                        + foodId));
    }

    @Override
    public List<Food> getAllFoods() {

        logger.info("Fetching all foods");

        return foodRepository.findAll();
    }

    @Override
    public Food updateFood(
            Integer foodId,
            FoodDTO dto) {
 
        logger.info("Updating Food with Id : {}", foodId);
 
        Food existingFood = foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new FoodNotFoundException(
                                "Food not found with id : "
                                        + foodId));
 
        if (dto.getFoodName() != null) {
            existingFood.setFoodName(dto.getFoodName());
        }
 
        if (dto.getCategory() != null) {
            existingFood.setCategory(dto.getCategory());
        }
 
        if (dto.getPrice() != null) {
            existingFood.setPrice(dto.getPrice());
        }
 
        if (dto.getDescription() != null) {
            existingFood.setDescription(dto.getDescription());
        }
 
        if (dto.getAvailable() != null) {
            existingFood.setAvailable(dto.getAvailable());
        }
 
        return foodRepository.save(existingFood);
    }
 
 

    @Override
    public void deleteFood(Integer foodId) {

        logger.info("Deleting Food with Id : {}", foodId);

        Food food = foodRepository.findById(foodId)
                .orElseThrow(() ->
                        new FoodNotFoundException(
                                "Food not found with id : "
                                        + foodId));

        foodRepository.delete(food);
    }

    @Override
    public List<Food> getFoodByCategory(Food.category category) {

        logger.info("Fetching foods by category : {}", category);

        List<Food> foods =
                foodRepository.findByCategory(category);

        if (foods.isEmpty()) {
            throw new FoodNotFoundException(
                    "No food found with category : " + category);
        }

        return foods;
    }

    @Override
    public List<Food> getFoodBelowPrice(Double price) {

        logger.info("Fetching foods below price : {}", price);

        List<Food> foods =
                foodRepository.findByPriceLessThan(price);

        if (foods.isEmpty()) {
            throw new FoodNotFoundException(
                    "No food found below price : " + price);
        }

        return foods;
    }

    @Override
    public List<Food> getAvailableFoods() {

        logger.info("Fetching available foods");

        return foodRepository.findByAvailableTrue();
    }

    @Override
    public List<Food> getFoodBetweenPrices(
            Double minPrice,
            Double maxPrice) {

        logger.info("Fetching foods between {} and {}",
                minPrice, maxPrice);

        List<Food> foods =
                foodRepository.findByPriceBetween(
                        minPrice, maxPrice);

        if (foods.isEmpty()) {
            throw new FoodNotFoundException(
                    "No food found between prices : "
                            + minPrice + " and " + maxPrice);
        }

        return foods;
    }
    
    @Override
    public List<Food> getFoodByCategoryAndPrice(
            Food.category category,
            Double price) {

        logger.info(
                "Fetching foods by category {} and below price {}",
                category,
                price);

        List<Food> foods =
                foodRepository.findByCategoryAndPriceLessThan(
                        category,
                        price);

        if (foods.isEmpty()) {

            throw new FoodNotFoundException(
                    "No food found for category "
                            + category
                            + " below price "
                            + price);
        }

        return foods;
    }

}