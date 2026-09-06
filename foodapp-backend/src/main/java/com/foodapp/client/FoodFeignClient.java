package com.foodapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.foodapp.dto.FoodDTO;

@FeignClient(name="food-service"
)
public interface FoodFeignClient {
    @GetMapping("admin/{foodId}")
    public FoodDTO getFoodById(@PathVariable int foodId);
    

}
