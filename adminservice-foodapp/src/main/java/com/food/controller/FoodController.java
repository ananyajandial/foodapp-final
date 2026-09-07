package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.dto.FoodDTO;
import com.food.entity.Food;
import com.food.entity.Food.category;
import com.food.service.FoodService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/admin")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Operation(summary = "Add a new food")
    @PostMapping("/add")
    public ResponseEntity<Food> addFood(@RequestBody Food food) {

        Food savedFood = foodService.addFood(food);

        return new ResponseEntity<>(savedFood,
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get food by id")
    @GetMapping("/food/{foodId}")
    public ResponseEntity<Food> getFoodById(
            @PathVariable Integer foodId) {

        Food food = foodService.getFoodById(foodId);

        return new ResponseEntity<>(food,
                HttpStatus.OK);
    }

    @Operation(summary = "Get all foods")
    @GetMapping("/all")
    public ResponseEntity<List<Food>> getAllFoods() {

        List<Food> foods =
                foodService.getAllFoods();

        return new ResponseEntity<>(foods,
                HttpStatus.OK);
    }

    @Operation(summary = "Update food details")
    @PatchMapping("/update/{foodId}")
    public ResponseEntity<Food> updateFood(
            @PathVariable Integer foodId,
            @RequestBody FoodDTO dto) {

        Food updatedFood =
                foodService.updateFood(foodId, dto);

        return new ResponseEntity<>(
                updatedFood,
                HttpStatus.OK);
    }

    @Operation(summary = "Delete food by id")
    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<String> deleteFood(
            @PathVariable Integer foodId) {

        foodService.deleteFood(foodId);

        return new ResponseEntity<>(
                "Food Deleted Successfully",
                HttpStatus.OK);
    }

    @Operation(summary = "Get foods by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Food>> getFoodByCategory(
            @PathVariable category category) {

        List<Food> foods =
                foodService.getFoodByCategory(category);

        return new ResponseEntity<>(foods,
                HttpStatus.OK);
    }

    @Operation(summary = "Get foods below price")
    @GetMapping("/belowprice/{price}")
    public ResponseEntity<List<Food>> getFoodBelowPrice(
            @PathVariable Double price) {

        List<Food> foods =
                foodService.getFoodBelowPrice(price);

        return new ResponseEntity<>(foods,
                HttpStatus.OK);
    }

    @Operation(summary = "Get available foods")
    @GetMapping("/available")
    public ResponseEntity<List<Food>> getAvailableFoods() {

        List<Food> foods =
                foodService.getAvailableFoods();

        return new ResponseEntity<>(foods,
                HttpStatus.OK);
    }

    @Operation(summary = "Get foods between two prices")
    @GetMapping("/between/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Food>> getFoodBetweenPrices(
            @PathVariable Double minPrice,
            @PathVariable Double maxPrice) {

        List<Food> foods =
                foodService.getFoodBetweenPrices(
                        minPrice,
                        maxPrice);

        return new ResponseEntity<>(foods,
                HttpStatus.OK);
    }

    @Operation(summary = "Get foods by category and below price")
    @GetMapping("/filter")
    public ResponseEntity<List<Food>> getFoodByCategoryAndPrice(
            @RequestParam category c,
            @RequestParam Double p) {

        List<Food> foods = foodService.getFoodByCategoryAndPrice(c,p);

        return new ResponseEntity<>(
                foods,
                HttpStatus.OK);
    }
}