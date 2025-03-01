package com.company.project.Zomato.ZomatoApp.strategies.Impl;

import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.repositories.RestaurantRepository;
import com.company.project.Zomato.ZomatoApp.strategies.RestaurantMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class RestaurantMatchingNearestRestaurantStrategy implements RestaurantMatchingStrategy {

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findTenNearestRestaurants(OrderItemRequest orderItemRequest) {
       return restaurantRepository.findTenNearestRestaurants(orderItemRequest.getPickupLocation());
    }
}
