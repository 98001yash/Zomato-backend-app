package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.dto.CustomerDto;
import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.entities.Customer;
import com.company.project.Zomato.ZomatoApp.entities.OrderItem;

public interface RatingService {

    RestaurantDto rateRestaurant(OrderItem orderItem, Integer rating);
    CustomerDto rateCustomer(OrderItem orderItem, Integer rating);

    void createNewRating(OrderItem orderItem);
}
