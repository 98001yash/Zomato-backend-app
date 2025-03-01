package com.company.project.Zomato.ZomatoApp.strategies.Impl;


import com.company.project.Zomato.ZomatoApp.dto.OrderItemRequestDto;
import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import com.company.project.Zomato.ZomatoApp.repositories.RestaurantRepository;
import com.company.project.Zomato.ZomatoApp.strategies.RestaurantMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantMatchingHighestRatedRestaurantStrategy implements RestaurantMatchingStrategy {

      private final RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> findTenNearestRestaurants(OrderItemRequest orderItemRequest) {
      return restaurantRepository.findTenNearByTopRatedRestaurant(orderItemRequest.getPickupLocation());
    }
}
