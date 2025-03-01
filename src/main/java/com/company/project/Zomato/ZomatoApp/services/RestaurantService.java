package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.dto.CustomerDto;
import com.company.project.Zomato.ZomatoApp.dto.OrderItemDto;
import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.entities.OrderItem;
import com.company.project.Zomato.ZomatoApp.entities.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RestaurantService {


    OrderItemDto  acceptOrderItem(Long orderItemRequestId);
    OrderItemDto cancelOrderItem(Long orderItemId);

    OrderItemDto startOrderItem(Long orderItemId, String otp);

    OrderItemDto endOrderItem(Long orderItemId);

    CustomerDto rateCustomer(Long orderItem, Integer rating);

    RestaurantDto getMyProfile();
    Page<OrderItemDto> getAllMyOrderItem(PageRequest pageRequest);

    Restaurant getCurrentRestaurant();
    Restaurant updateRestaurantAvailability(Restaurant restaurant, boolean available);

    Restaurant createNewRestaurant(Restaurant restaurant);

}
