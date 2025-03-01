package com.company.project.Zomato.ZomatoApp.strategies;

import com.company.project.Zomato.ZomatoApp.dto.OrderItemRequestDto;
import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;

public interface OrderItemFareCalculationStrategy {

     double  RIDE_FARE_MULTIPLIER = 10;

    double calculateFare(OrderItemRequest orderItemRequest);
}
