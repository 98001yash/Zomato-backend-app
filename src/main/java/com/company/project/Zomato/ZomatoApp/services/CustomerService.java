package com.company.project.Zomato.ZomatoApp.services;

import com.company.project.Zomato.ZomatoApp.dto.CustomerDto;
import com.company.project.Zomato.ZomatoApp.dto.OrderItemDto;
import com.company.project.Zomato.ZomatoApp.dto.OrderItemRequestDto;
import com.company.project.Zomato.ZomatoApp.dto.RestaurantDto;
import com.company.project.Zomato.ZomatoApp.entities.Customer;
import com.company.project.Zomato.ZomatoApp.entities.User;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CustomerService {


    OrderItemRequestDto requestOrderItem(OrderItemRequestDto orderItemRequestDto);

 OrderItemDto cancelOrderItem(Long orderItemId);
    RestaurantDto rateRestaurant(Long orderItemId, Integer rating);
    List<RestaurantDto> getAllRestaurants();
    CustomerDto getMyProfile();
    List<OrderItemDto> getAllMyOrderItem(PageRequest pageRequest);
    Customer createNewCustomer(User user);


    Customer getCurrentCustomer();
}
