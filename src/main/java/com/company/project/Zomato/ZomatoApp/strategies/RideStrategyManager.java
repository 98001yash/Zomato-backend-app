package com.company.project.Zomato.ZomatoApp.strategies;


import com.company.project.Zomato.ZomatoApp.strategies.Impl.OrderItemFareDefaultFareCalculationStrategy;
import com.company.project.Zomato.ZomatoApp.strategies.Impl.OrderItemFareSurgedPricingFareCalculationStrategy;
import com.company.project.Zomato.ZomatoApp.strategies.Impl.RestaurantMatchingHighestRatedRestaurantStrategy;
import com.company.project.Zomato.ZomatoApp.strategies.Impl.RestaurantMatchingNearestRestaurantStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final RestaurantMatchingHighestRatedRestaurantStrategy highestRatedRestaurantStrategy;
    private final RestaurantMatchingNearestRestaurantStrategy restaurantMatchingNearestRestaurantStrategy;
    private final OrderItemFareSurgedPricingFareCalculationStrategy orderItemFareSurgedPricingFareCalculationStrategy;
    private final OrderItemFareDefaultFareCalculationStrategy orderItemFareDefaultFareCalculationStrategy;


    public RestaurantMatchingStrategy restaurantMatchingStrategy(double orderItemRating) {
        if(orderItemRating >= 4.8){
        return highestRatedRestaurantStrategy;
        }else {
            return restaurantMatchingNearestRestaurantStrategy;
        }
    }
    public OrderItemFareCalculationStrategy orderItemFareCalculationStrategy(){
      LocalTime surgeStartTime = LocalTime.of(10,0);
      LocalTime surgeEndTime  = LocalTime.of(21,0);
    LocalTime currentTime = LocalTime.now();

    boolean isSurgeTime = currentTime.isAfter(surgeStartTime)&& currentTime.isBefore(surgeEndTime);

    if(isSurgeTime){
        return orderItemFareSurgedPricingFareCalculationStrategy;
    }else {
        return orderItemFareDefaultFareCalculationStrategy;
    }
    }
}
