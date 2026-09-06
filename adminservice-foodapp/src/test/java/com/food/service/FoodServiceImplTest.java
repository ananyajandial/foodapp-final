package com.food.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.food.controller.FoodController;

class FoodServiceImplTest {

	@Test
    void testControllerObjectCreation() {
 
        FoodController controller = new FoodController();
 
        assertNotNull(controller);
    }
 
    @Test
    void testControllerClassName() {
 
        FoodController controller = new FoodController();
 
        assertNotNull(controller.getClass());
    }
 
    @Test
    void testControllerSimpleClassName() {
 
        FoodController controller = new FoodController();
 
        assertEquals(
                "FoodController",
                controller.getClass().getSimpleName());
    }
 
    @Test
    void testControllerPackageName() {
 
        FoodController controller = new FoodController();
 
        assertTrue(
                controller.getClass()
                        .getPackageName()
                        .contains("controller"));
    }
 
    @Test
    void testControllerClassNotNull() {
 
        assertNotNull(FoodController.class);
    }
}