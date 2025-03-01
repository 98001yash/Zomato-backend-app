package com.company.project.Zomato.ZomatoApp.strategies;

import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;

import java.util.List;

public interface RestaurantMatchingStrategy {

     List<Restaurant> findTenNearestRestaurants(OrderItemRequest orderItemRequest);
}
