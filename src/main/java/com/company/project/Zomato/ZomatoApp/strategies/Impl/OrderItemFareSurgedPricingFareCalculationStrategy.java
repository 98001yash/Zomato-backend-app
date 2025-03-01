package com.company.project.Zomato.ZomatoApp.strategies.Impl;


import com.company.project.Zomato.ZomatoApp.entities.OrderItemRequest;
import com.company.project.Zomato.ZomatoApp.services.DistanceService;
import com.company.project.Zomato.ZomatoApp.strategies.OrderItemFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemFareSurgedPricingFareCalculationStrategy implements OrderItemFareCalculationStrategy {


      private final DistanceService distanceService;
      private static final double SURGE_FACTOR = 2;


    @Override
    public double calculateFare(OrderItemRequest orderItemRequest) {
        Double distance = distanceService.calculateDistance(orderItemRequest.getPickupLocation(),
                orderItemRequest.getDropOffLocation());
        return distance*RIDE_FARE_MULTIPLIER*SURGE_FACTOR;
    }
}
