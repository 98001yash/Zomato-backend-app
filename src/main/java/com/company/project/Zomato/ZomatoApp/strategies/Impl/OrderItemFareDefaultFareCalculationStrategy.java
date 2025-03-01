package com.company.project.Zomato.ZomatoApp.strategies.Impl;

import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.services.DistanceService;
import com.company.project.Zomato.ZomatoApp.strategies.OrderItemFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor

public class OrderItemFareDefaultFareCalculationStrategy implements OrderItemFareCalculationStrategy {

    private final DistanceService distanceService;

    @Override
    public double calculateFare(OrderItemRequest orderItemRequest) {
      Double distance = distanceService.calculateDistance(orderItemRequest.getPickupLocation(),
              orderItemRequest.getDropOffLocation());
      return distance*RIDE_FARE_MULTIPLIER;
    }
}
