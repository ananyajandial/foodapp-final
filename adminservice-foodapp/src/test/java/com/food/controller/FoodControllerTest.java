package com.food.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class FoodControllerTest {

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
}